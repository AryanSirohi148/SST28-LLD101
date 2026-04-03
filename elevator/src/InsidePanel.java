import java.util.ArrayList;
import java.util.List;

public class InsidePanel {
    private final String elevatorId;
    private final List<Button> buttons;

    public InsidePanel(String elevatorId, int maxFloor) {
        this.elevatorId = elevatorId;
        this.buttons = new ArrayList<>();

        for (int i = 0; i <= maxFloor; i++) {
            buttons.add(new Button(ButtonType.FLOOR, i));
        }

        buttons.add(new Button(ButtonType.OPEN, 0));
        buttons.add(new Button(ButtonType.CLOSE, 0));
        buttons.add(new Button(ButtonType.ALARM, 0));
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public List<Button> getButtons() {
        return new ArrayList<>(buttons);
    }

    public void pressButton(ButtonType buttonType, int floor, Elevator elevator) {
        if (buttonType == ButtonType.FLOOR) {
            System.out.println("    [User in Elevator-" + elevatorId + "] Pressed Floor-" + floor);
            try {
                elevator.addRequest(floor, 0); // weight already counted when entering
            } catch (Exception ex) {
                System.out.println("    Error adding request: " + ex.getMessage());
            }
        } else if (buttonType == ButtonType.OPEN) {
            System.out.println("    [User in Elevator-" + elevatorId + "] Pressed OPEN");
            elevator.openDoors();
        } else if (buttonType == ButtonType.CLOSE) {
            System.out.println("    [User in Elevator-" + elevatorId + "] Pressed CLOSE");
            elevator.closeDoors();
        } else if (buttonType == ButtonType.ALARM) {
            System.out.println("    [User in Elevator-" + elevatorId + "] Pressed ALARM");
            elevator.triggerAlarm();
        }
    }

    @Override
    public String toString() {
        return "InsidePanel{elevatorId='" + elevatorId + "'}";
    }
}
