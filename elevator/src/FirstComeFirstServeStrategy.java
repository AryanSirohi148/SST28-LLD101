import java.util.List;

public class FirstComeFirstServeStrategy implements ElevatorSchedulingStrategy {
    @Override
    public Elevator selectElevator(ElevatorRequest request, List<Elevator> availableElevators) {
        for (Elevator elevator : availableElevators) {
            if (elevator.getState() != ElevatorState.MAINTENANCE) {
                return elevator;
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "FirstComeFirstServe";
    }
}
