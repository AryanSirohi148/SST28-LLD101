public class ConsoleBookingObserver implements BookingObserver {
    @Override
    public void onTicketBooked(Ticket ticket) {
        System.out.println("[Observer] Ticket booked: " + ticket.getId() + ", seats=" + ticket.getSeatIds());
    }

    @Override
    public void onTicketCancelled(Ticket ticket) {
        System.out.println("[Observer] Ticket cancelled: " + ticket.getId() + ", refund initiated.");
    }
}
