// Creates appropriate PricingComponent instances for rooms and add-ons.
public class PricingFactory {

    public static PricingComponent roomOf(int roomType) {
        return switch (roomType) {
            case LegacyRoomTypes.SINGLE -> new SingleRoom();
            case LegacyRoomTypes.DOUBLE -> new DoubleRoom();
            case LegacyRoomTypes.TRIPLE -> new TripleRoom();
            default -> throw new IllegalArgumentException();
        };
    }

    public static PricingComponent addOnOf(AddOn a) {
        return switch (a) {
            case MESS -> new MessAddOn();
            case LAUNDRY -> new LaundryAddOn();
            case GYM -> new GymAddOn();
        };
    }
}