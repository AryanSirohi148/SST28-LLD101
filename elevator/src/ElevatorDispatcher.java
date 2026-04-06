import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ElevatorDispatcher {
    private final List<Elevator> elevatorList;
    private final ElevatorSchedulingStrategy strategy;
    private final Map<String, OutsidePanel> outPanels;
    private final Map<String, InsidePanel> inPanels;

    public ElevatorDispatcher(ElevatorSchedulingStrategy strategy) {
        this.elevatorList = new ArrayList<>();
        this.strategy = Objects.requireNonNull(strategy);
        this.outPanels = new HashMap<>();
        this.inPanels = new HashMap<>();
    }

    public void addElevator(Elevator elevator, int maxFloor) {
        elevatorList.add(elevator);
        inPanels.put(elevator.getId(), new InsidePanel(elevator.getId(), maxFloor));
    }

    public void addOutsidePanel(OutsidePanel panel) {
        outPanels.put("FLOOR-" + panel.getFloor(), panel);
    }

    public synchronized void requestElevator(int fromFloor, Direction direction, double weight) {
        List<Elevator> available = new ArrayList<>();
        for (Elevator elevator : elevatorList) {
            if (elevator.getState() != ElevatorState.MAINTENANCE && elevator.getCurrentWeight() + weight <= 700.0) {
                available.add(elevator);
            }
        }

        if (available.isEmpty()) {
            System.out.println("No elevator available at floor " + fromFloor);
            return;
        }

        ElevatorRequest request = new ElevatorRequest(fromFloor, direction == Direction.UP ? fromFloor + 1 : fromFloor - 1, direction, weight);
        Elevator selected = strategy.selectElevator(request, available);

        if (selected != null) {
            System.out.println("Dispatching " + selected.getId() + " (" + strategy.name() + ")");
            try {
                selected.addRequest(fromFloor, weight);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public ElevatorSchedulingStrategy getStrategy() {
        return strategy;
    }

    public List<Elevator> getAllElevators() {
        return new ArrayList<>(elevatorList);
    }

    public InsidePanel getInsidePanel(String elevatorId) {
        return inPanels.get(elevatorId);
    }

    public OutsidePanel getOutsidePanel(int floor) {
        return outPanels.get("FLOOR-" + floor);
    }

    @Override
    public String toString() {
        return "Dispatcher{count=" + elevatorList.size() + ", strategy=" + strategy.name() + "}";
    }
}
