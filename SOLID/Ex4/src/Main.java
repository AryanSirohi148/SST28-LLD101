import booking.AddOn;
import booking.BookingRequest;
import booking.BookingService;
import pricing.LegacyRoomTypes;
import repository.FakeBookingRepo;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, (List<AddOn>) List.of(AddOn.LAUNDRY, AddOn.MESS));
        BookingService bookingService = new BookingService(new FakeBookingRepo());
        bookingService.process(req);
    }
}
