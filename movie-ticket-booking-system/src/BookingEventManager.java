import java.util.ArrayList;
import java.util.List;

public class BookingEventManager {
    private final List<BookingObserver> observers = new ArrayList<>();

    public void register(BookingObserver observer) {
        observers.add(observer);
    }

    public void notifyBooked(Ticket ticket) {
        for (BookingObserver observer : observers) {
            observer.onTicketBooked(ticket);
        }
    }

    public void notifyCancelled(Ticket ticket) {
        for (BookingObserver observer : observers) {
            observer.onTicketCancelled(ticket);
        }
    }
}
