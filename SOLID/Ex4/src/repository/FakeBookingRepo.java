package repository;

import booking.BookingRepo;
import booking.BookingRequest;
import common.Money;

public class FakeBookingRepo implements BookingRepo {
    public void save(String id, BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Saved booking: " + id);
    }
}
