import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final InMemoryStore store;
    private final PricingEngine pricingEngine;
    private final PaymentService paymentService;
    private final BookingEventManager eventManager;

    public BookingService(
        InMemoryStore store,
        PricingEngine pricingEngine,
        PaymentService paymentService,
        BookingEventManager eventManager
    ) {
        this.store = store;
        this.pricingEngine = pricingEngine;
        this.paymentService = paymentService;
        this.eventManager = eventManager;
    }

    public Ticket bookTickets(String userId, String showId, List<String> seatIds, PaymentMode mode) {
        User user = store.getUser(userId);
        Show show = store.getShow(showId);

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("At least one seat must be selected.");
        }

        // During payment window, seats remain locked for the user.
        boolean locked = show.lockSeats(userId, seatIds, 120);
        if (!locked) {
            throw new IllegalStateException("Some selected seats are no longer available.");
        }

        List<Seat> seats = show.getSeats(seatIds);
        double totalAmount = 0;
        for (Seat seat : seats) {
            totalAmount += pricingEngine.calculatePrice(show, seat);
        }

        Payment payment = paymentService.makePayment(userId, totalAmount, mode);
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            show.releaseLocks(userId, seatIds);
            throw new IllegalStateException("Payment failed.");
        }

        boolean confirmed = show.confirmBooking(userId, seatIds);
        if (!confirmed) {
            throw new IllegalStateException("Booking confirmation failed due to lock timeout.");
        }

        Ticket ticket = new Ticket.Builder()
            .id(IdGenerator.ticketId())
            .userId(userId)
            .showId(showId)
            .seatIds(new ArrayList<>(seatIds))
            .totalAmount(Math.round(totalAmount * 100.0) / 100.0)
            .paymentId(payment.getId())
            .paymentMode(mode)
            .status(TicketStatus.BOOKED)
            .build();

        store.saveTicket(ticket);
        eventManager.notifyBooked(ticket);

        return ticket;
    }

    public Ticket cancelTicket(String userId, String ticketId) {
        Ticket ticket = store.getTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found: " + ticketId);
        }
        if (!ticket.getUserId().equals(userId)) {
            throw new IllegalStateException("Ticket does not belong to user.");
        }
        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Ticket already cancelled.");
        }

        Show show = store.getShow(ticket.getShowId());
        show.cancelBookedSeats(ticket.getSeatIds());

        paymentService.refund(ticket.getPaymentId());

        Ticket cancelledTicket = ticket.withCancelledStatus();
        store.saveTicket(cancelledTicket);
        eventManager.notifyCancelled(cancelledTicket);
        return cancelledTicket;
    }

    public java.util.Map<String, SeatState> showSeatMap(String showId) {
        Show show = store.getShow(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }
        return show.getSeatMapSnapshot();
    }

    public double estimateTotal(String showId, List<String> seatIds) {
        Show show = store.getShow(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }

        double total = 0;
        for (Seat seat : show.getSeats(seatIds)) {
            total += pricingEngine.calculatePrice(show, seat);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}
