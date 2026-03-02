package booking;

import common.Money;

public interface BookingRepo {

    void save(String id, BookingRequest req, Money monthly, Money deposit);
}
