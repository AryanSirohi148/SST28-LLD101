import java.util.Objects;

public class Button {
    private final ButtonType type;
    private final int floor;

    public Button(ButtonType type, int floor) {
        this.type = Objects.requireNonNull(type);
        this.floor = floor;
    }

    public ButtonType getType() {
        return type;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public String toString() {
        return type + "-" + floor;
    }
}
