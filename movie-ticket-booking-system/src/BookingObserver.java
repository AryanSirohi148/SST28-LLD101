public interface BookingObserver {
    void onTicketBooked(Ticket ticket);

    void onTicketCancelled(Ticket ticket);
}
