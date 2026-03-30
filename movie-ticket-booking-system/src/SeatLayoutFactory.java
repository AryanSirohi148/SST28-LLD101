import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeatLayoutFactory {
    private SeatLayoutFactory() {
    }

    // Example: BRONZE->20, SILVER->20, GOLD->10, DIAMOND->5
    public static List<Seat> createSimpleLayout(Map<SeatType, Integer> countByType) {
        List<Seat> seats = new ArrayList<>();

        for (Map.Entry<SeatType, Integer> entry : countByType.entrySet()) {
            SeatType type = entry.getKey();
            int count = entry.getValue();
            for (int i = 1; i <= count; i++) {
                String seatId = type.name().charAt(0) + String.valueOf(i);
                seats.add(new Seat(seatId, type));
            }
        }

        return seats;
    }
}
