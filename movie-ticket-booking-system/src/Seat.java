import java.util.Objects;

public class Seat {
    private final String id;
    private final SeatType seatType;

    public Seat(String id, SeatType seatType) {
        this.id = Objects.requireNonNull(id);
        this.seatType = Objects.requireNonNull(seatType);
    }

    public String getId() {
        return id;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    @Override
    public String toString() {
        return id + "(" + seatType + ")";
    }
}
