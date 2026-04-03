import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OutsidePanel {
    private final int floor;
    private final List<Button> buttons;

    public OutsidePanel(int floor) {
        this.floor = floor;
        this.buttons = new ArrayList<>();
        this.buttons.add(new Button(ButtonType.UP, floor));
        this.buttons.add(new Button(ButtonType.DOWN, floor));
    }

    public int getFloor() {
        return floor;
    }

    public List<Button> getButtons() {
        return new ArrayList<>(buttons);
    }

    public void pressButton(ButtonType buttonType, ElevatorDispatcher dispatcher, double weight) {
        if (buttonType == ButtonType.UP || buttonType == ButtonType.DOWN) {
            System.out.println("  [User at Floor-" + floor + "] Pressed " + buttonType + " button (weight: " + weight + "kg)");
            Direction direction = buttonType == ButtonType.UP ? Direction.UP : Direction.DOWN;
            dispatcher.requestElevator(floor, direction, weight);
        }
    }

    @Override
    public String toString() {
        return "OutsidePanel{floor=" + floor + "}";
    }
}
