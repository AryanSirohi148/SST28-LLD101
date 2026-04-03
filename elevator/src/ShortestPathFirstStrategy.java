import java.util.List;

public class ShortestPathFirstStrategy implements ElevatorSchedulingStrategy {
    @Override
    public Elevator selectElevator(ElevatorRequest request, List<Elevator> availableElevators) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : availableElevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - request.getFromFloor());

            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }

        return bestElevator;
    }

    @Override
    public String name() {
        return "ShortestPathFirst";
    }
}
