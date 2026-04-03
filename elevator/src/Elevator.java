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
    private final List<Integer> destinationFloors;
    private boolean doorsOpen;
    private boolean alarmTriggered;

    public Elevator(String id, int startFloor) {
        this.id = Objects.requireNonNull(id);
        this.currentFloor = startFloor;
        this.currentWeight = 0.0;
        this.state = ElevatorState.IDLE;
        this.destinationFloors = new ArrayList<>();
        this.doorsOpen = false;
        this.alarmTriggered = false;
    }

    public synchronized void addRequest(int destinationFloor, double weight) {
        if (state == ElevatorState.MAINTENANCE) {
            throw new IllegalStateException("Elevator " + id + " is under maintenance.");
        }

        if (currentWeight + weight > MAX_WEIGHT) {
            throw new IllegalStateException(
                "Weight limit exceeded. Current: " + currentWeight + "kg, Adding: "
                    + weight + "kg, Max: " + MAX_WEIGHT + "kg"
            );
        }

        if (!destinationFloors.contains(destinationFloor)) {
            destinationFloors.add(destinationFloor);
        }

        currentWeight += weight;
    }

    public synchronized void processNextFloor() {
        if (state == ElevatorState.MAINTENANCE || destinationFloors.isEmpty()) {
            return;
        }

        if (doorsOpen) {
            closeDoors();
        }

        Integer nextFloor = getNextDestination();
        if (nextFloor == null) {
            state = ElevatorState.IDLE;
            return;
        }

        if (nextFloor > currentFloor) {
            state = ElevatorState.MOVING_UP;
            System.out.println("  [Elevator-" + id + "] Moving UP to floor " + nextFloor);
        } else if (nextFloor < currentFloor) {
            state = ElevatorState.MOVING_DOWN;
            System.out.println("  [Elevator-" + id + "] Moving DOWN to floor " + nextFloor);
        }

        currentFloor = nextFloor;
        destinationFloors.remove(Integer.valueOf(nextFloor));

        System.out.println("  [Elevator-" + id + "] Arrived at floor " + currentFloor);
        openDoors();

        if (destinationFloors.isEmpty()) {
            currentWeight = 0.0;
            state = ElevatorState.IDLE;
        }
    }

    public synchronized void openDoors() {
        if (!doorsOpen && state != ElevatorState.MAINTENANCE) {
            doorsOpen = true;
            System.out.println("    >>> Doors OPENED at floor " + currentFloor);
        }
    }

    public synchronized void closeDoors() {
        if (doorsOpen) {
            doorsOpen = false;
            System.out.println("    >>> Doors CLOSED");
        }
    }

    public synchronized void triggerAlarm() {
        alarmTriggered = true;
        System.out.println("  [Elevator-" + id + "] ALARM TRIGGERED! Emergency stop.");
        closeDoors();
        state = ElevatorState.IDLE;
        destinationFloors.clear();
        currentWeight = 0.0;
    }

    public synchronized void enterMaintenance() {
        state = ElevatorState.MAINTENANCE;
        closeDoors();
        destinationFloors.clear();
        currentWeight = 0.0;
        System.out.println("  [Elevator-" + id + "] Entered MAINTENANCE mode.");
    }

    public synchronized void exitMaintenance() {
        state = ElevatorState.IDLE;
        alarmTriggered = false;
        System.out.println("  [Elevator-" + id + "] Exited MAINTENANCE mode.");
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
        return destinationFloors.size();
    }

    public synchronized List<Integer> getPendingDestinations() {
        return Collections.unmodifiableList(new ArrayList<>(destinationFloors));
    }

    private Integer getNextDestination() {
        if (destinationFloors.isEmpty()) {
            return null;
        }

        // Simple strategy: go to closest floor next
        return Collections.min(destinationFloors, Comparator.comparingInt(f -> Math.abs(f - currentFloor)));
    }

    @Override
    public String toString() {
        return "Elevator{" +
            "id='" + id + '\'' +
            ", floor=" + currentFloor +
            ", state=" + state +
            ", weight=" + currentWeight + "/" + MAX_WEIGHT + "kg" +
            ", pending=" + destinationFloors.size() +
            '}';
    }
}
