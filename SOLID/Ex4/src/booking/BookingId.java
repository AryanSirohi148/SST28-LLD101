package booking;

import java.util.Random;

public class BookingId {

    public static String generate(){
        return "H-" + (7000 + new Random(1).nextInt(1000));
    }
}
