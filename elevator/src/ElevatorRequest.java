import java.util.Objects;

public class ElevatorRequest {
    private final int fromFloor;
    private final int toFloor;
    private final Direction direction;
    private final double weight;

    public ElevatorRequest(int fromFloor, int toFloor, Direction direction, double weight) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.direction = Objects.requireNonNull(direction);
        this.weight = weight;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "ElevatorRequest{" +
            "from=" + fromFloor +
            ", to=" + toFloor +
            ", dir=" + direction +
            ", weight=" + weight + "kg" +
            '}';
    }
}
