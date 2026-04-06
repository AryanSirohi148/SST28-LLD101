import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Elevator {
    private static final double MAX_WEIGHT = 700.0;

    private final String id;
    private int currentFloor;
    private double currentWeight;
    private ElevatorState state;
    private final List<Integer> targets;
    private boolean doorsOpen;
    private boolean alarmTriggered;

    public Elevator(String id, int startFloor) {
        this.id = Objects.requireNonNull(id);
        this.currentFloor = startFloor;
        this.currentWeight = 0.0;
        this.state = ElevatorState.IDLE;
        this.targets = new ArrayList<>();
        this.doorsOpen = false;
        this.alarmTriggered = false;
    }

    public synchronized void addRequest(int floor, double weight) {
        if (state == ElevatorState.MAINTENANCE) {
            throw new IllegalStateException("Elevator " + id + " is under maintenance.");
        }

        if (currentWeight + weight > MAX_WEIGHT) {
            throw new IllegalStateException(
                "Weight limit exceeded. Current: " + currentWeight + "kg, Adding: "
                    + weight + "kg, Max: " + MAX_WEIGHT + "kg"
            );
        }

        if (!targets.contains(floor)) {
            targets.add(floor);
        }

        currentWeight += weight;
    }

    public synchronized void processNextFloor() {
        if (state == ElevatorState.MAINTENANCE || targets.isEmpty()) {
            return;
        }

        if (doorsOpen) {
            closeDoors();
        }

        Integer next = getNextDestination();
        if (next == null) {
            state = ElevatorState.IDLE;
            return;
        }

        if (next > currentFloor) {
            state = ElevatorState.MOVING_UP;
            System.out.println("Elevator " + id + " going UP to " + next);
        } else if (next < currentFloor) {
            state = ElevatorState.MOVING_DOWN;
            System.out.println("Elevator " + id + " going DOWN to " + next);
        }

        currentFloor = next;
        targets.remove(Integer.valueOf(next));

        System.out.println("Elevator " + id + " reached " + currentFloor);
        openDoors();

        if (targets.isEmpty()) {
            currentWeight = 0.0;
            state = ElevatorState.IDLE;
        }
    }

    public synchronized void openDoors() {
        if (!doorsOpen && state != ElevatorState.MAINTENANCE) {
            doorsOpen = true;
            System.out.println("Doors opened at " + currentFloor);
        }
    }

    public synchronized void closeDoors() {
        if (doorsOpen) {
            doorsOpen = false;
            System.out.println("Doors closed");
        }
    }

    public synchronized void triggerAlarm() {
        alarmTriggered = true;
        System.out.println("Elevator " + id + " alarm triggered");
        closeDoors();
        state = ElevatorState.IDLE;
        targets.clear();
        currentWeight = 0.0;
    }

    public synchronized void enterMaintenance() {
        state = ElevatorState.MAINTENANCE;
        closeDoors();
        targets.clear();
        currentWeight = 0.0;
        System.out.println("Elevator " + id + " in maintenance");
    }

    public synchronized void exitMaintenance() {
        state = ElevatorState.IDLE;
        alarmTriggered = false;
        System.out.println("Elevator " + id + " back to normal");
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized ElevatorState getState() {
        return state;
    }

    public synchronized double getCurrentWeight() {
        return currentWeight;
    }

    public synchronized boolean isDoorsOpen() {
        return doorsOpen;
    }

    public synchronized boolean isAlarmTriggered() {
        return alarmTriggered;
    }

    public synchronized int getPendingRequestCount() {
        return targets.size();
    }

    public synchronized List<Integer> getPendingDestinations() {
        return Collections.unmodifiableList(new ArrayList<>(targets));
    }

    private Integer getNextDestination() {
        if (targets.isEmpty()) {
            return null;
        }

        // Simple strategy: go to closest floor next
        return Collections.min(targets, Comparator.comparingInt(f -> Math.abs(f - currentFloor)));
    }

    @Override
    public String toString() {
        return "Elevator{" +
            "id='" + id + '\'' +
            ", floor=" + currentFloor +
            ", state=" + state +
            ", weight=" + currentWeight + "/" + MAX_WEIGHT + "kg" +
            ", pending=" + targets.size() +
            '}';
    }
}
