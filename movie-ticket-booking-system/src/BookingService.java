import java.util.ArrayList;
import java.util.List;

// handles ticket booking and cancellation
public class BookingService {
    private final InMemoryStore dataStore;
    private final PricingEngine pricingEngine;
    private final PaymentService paymentService;
    private final BookingEventManager eventManager;

    public BookingService(
        InMemoryStore dataStore,
        PricingEngine pricingEngine,
        PaymentService paymentService,
        BookingEventManager eventManager
    ) {
        this.dataStore = dataStore;
        this.pricingEngine = pricingEngine;
        this.paymentService = paymentService;
        this.eventManager = eventManager;
    }

    public Ticket bookTickets(String userId, String showId, List<String> seatIds, PaymentMode mode) {
        User user = dataStore.getUser(userId);
        Show show = dataStore.getShow(showId);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }
        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("Select at least one seat");
        }

        // During payment window, seats remain locked for the user.
        boolean locked = show.lockSeats(userId, seatIds, 120);
        if (!locked) {
            throw new IllegalStateException("Some selected seats are no longer available.");
        }

        List<Seat> seats = show.getSeats(seatIds);
        double amount = 0;
        for (Seat seat : seats) {
            amount += pricingEngine.calculatePrice(show, seat);
        }

        Payment payment = paymentService.makePayment(userId, amount, mode);
        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            show.releaseLocks(userId, seatIds);
            throw new IllegalStateException("Payment failed");
        }

        boolean confirmed = show.confirmBooking(userId, seatIds);
        if (!confirmed) {
            throw new IllegalStateException("Booking failed");
        }

        Ticket ticket = new Ticket.Builder()
            .id(IdGenerator.ticketId())
            .userId(userId)
            .showId(showId)
            .seatIds(new ArrayList<>(seatIds))
            .totalAmount(Math.round(amount * 100.0) / 100.0)
            .paymentId(payment.getId())
            .paymentMode(mode)
            .status(TicketStatus.BOOKED)
            .build();

        dataStore.saveTicket(ticket);
        eventManager.notifyBooked(ticket);

        return ticket;
    }

    public Ticket cancelTicket(String userId, String ticketId) {
        Ticket ticket = dataStore.getTicket(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found");
        }
        if (!ticket.getUserId().equals(userId)) {
            throw new IllegalStateException("Invalid user for ticket");
        }
        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Already cancelled");
        }

        Show show = dataStore.getShow(ticket.getShowId());
        show.cancelBookedSeats(ticket.getSeatIds());

        paymentService.refund(ticket.getPaymentId());

        Ticket cancelledTicket = ticket.withCancelledStatus();
        dataStore.saveTicket(cancelledTicket);
        eventManager.notifyCancelled(cancelledTicket);
        return cancelledTicket;
    }

    public java.util.Map<String, SeatState> showSeatMap(String showId) {
        Show show = dataStore.getShow(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }
        return show.getSeatMapSnapshot();
    }

    public double estimateTotal(String showId, List<String> seatIds) {
        Show show = dataStore.getShow(showId);
        if (show == null) {
            throw new IllegalArgumentException("Show not found");
        }

        double total = 0;
        for (Seat seat : show.getSeats(seatIds)) {
            total += pricingEngine.calculatePrice(show, seat);
        }
        return Math.round(total * 100.0) / 100.0;
    }
}
