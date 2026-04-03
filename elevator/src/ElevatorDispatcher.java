import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ElevatorDispatcher {
    private final List<Elevator> elevators;
    private final ElevatorSchedulingStrategy strategy;
    private final Map<String, OutsidePanel> outsidePanels;
    private final Map<String, InsidePanel> insidePanels;

    public ElevatorDispatcher(ElevatorSchedulingStrategy strategy) {
        this.elevators = new ArrayList<>();
        this.strategy = Objects.requireNonNull(strategy);
        this.outsidePanels = new HashMap<>();
        this.insidePanels = new HashMap<>();
    }

    public void addElevator(Elevator elevator, int maxFloor) {
        elevators.add(elevator);
        insidePanels.put(elevator.getId(), new InsidePanel(elevator.getId(), maxFloor));
    }

    public void addOutsidePanel(OutsidePanel panel) {
        outsidePanels.put("FLOOR-" + panel.getFloor(), panel);
    }

    public synchronized void requestElevator(int fromFloor, Direction direction, double weight) {
        List<Elevator> available = new ArrayList<>();
        for (Elevator elevator : elevators) {
            if (elevator.getState() != ElevatorState.MAINTENANCE && elevator.getCurrentWeight() + weight <= 700.0) {
                available.add(elevator);
            }
        }

        if (available.isEmpty()) {
            System.out.println("  [Dispatcher] No available elevator for request from floor " + fromFloor);
            return;
        }

        ElevatorRequest request = new ElevatorRequest(fromFloor, direction == Direction.UP ? fromFloor + 1 : fromFloor - 1, direction, weight);
        Elevator selected = strategy.selectElevator(request, available);

        if (selected != null) {
            System.out.println("  [Dispatcher] Dispatching " + selected.getId() + " using strategy: " + strategy.name());
            try {
                selected.addRequest(fromFloor, weight);
            } catch (Exception ex) {
                System.out.println("  [Dispatcher] Error: " + ex.getMessage());
            }
        }
    }

    public ElevatorSchedulingStrategy getStrategy() {
        return strategy;
    }

    public List<Elevator> getAllElevators() {
        return new ArrayList<>(elevators);
    }

    public InsidePanel getInsidePanel(String elevatorId) {
        return insidePanels.get(elevatorId);
    }

    public OutsidePanel getOutsidePanel(int floor) {
        return outsidePanels.get("FLOOR-" + floor);
    }

    @Override
    public String toString() {
        return "ElevatorDispatcher{" +
            "elevators=" + elevators.size() +
            ", strategy=" + strategy.name() +
            '}';
    }
}
