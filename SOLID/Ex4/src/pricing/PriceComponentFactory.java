package pricing;

import booking.AddOn;
import booking.BookingRequest;

import java.util.ArrayList;
import java.util.List;

public class PriceComponentFactory {

    public static List<PricingComponent> getComponent(BookingRequest req){
        List<PricingComponent> list = new ArrayList<>();

        switch (req.roomType) {
            case LegacyRoomTypes.SINGLE -> list.add(new SingleRoom());
            case LegacyRoomTypes.DOUBLE -> list.add(new DoubleRoom());
            case LegacyRoomTypes.TRIPLE -> list.add(new TripleRoom());
//            default -> base = 16000.0;
        }

        for (AddOn a : req.addOns) {
            if (a == AddOn.MESS) list.add(new MessAddOn());
            else if (a == AddOn.LAUNDRY) list.add(new LaundryAddOn());
            else if (a == AddOn.GYM) list.add(new GymAddOn());
        }

        return list;
    }
}
