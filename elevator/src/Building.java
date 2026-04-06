import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Building {
    private final String name;
    private final int numFloors;
    private final List<Floor> floorList;
    private final ElevatorDispatcher dispatcher;

    // initialize building with floors and dispatcher
    public Building(String name, int numFloors, ElevatorSchedulingStrategy strategy) {
        this.name = Objects.requireNonNull(name);
        this.numFloors = numFloors;
        this.floorList = new ArrayList<>();
        this.dispatcher = new ElevatorDispatcher(strategy);

        for (int i = 0; i <= numFloors; i++) {
            if (i == 0) {
                floorList.add(new Floor(i, "Ground"));
            } else {
                floorList.add(new Floor(i, "F" + i));
            }
            dispatcher.addOutsidePanel(new OutsidePanel(i));
        }
    }

    public void addElevator(String elevatorId) {
        Elevator elevator = new Elevator(elevatorId, 0);
        dispatcher.addElevator(elevator, numFloors);
    }

    public ElevatorDispatcher getDispatcher() {
        return dispatcher;
    }

    public List<Elevator> getAllElevators() {
        return dispatcher.getAllElevators();
    }

    public InsidePanel getInsidePanel(String elevatorId) {
        return dispatcher.getInsidePanel(elevatorId);
    }

    public OutsidePanel getOutsidePanel(int floor) {
        return dispatcher.getOutsidePanel(floor);
    }

    public void processAllElevators() {
        for (Elevator elevator : getAllElevators()) {
            if (elevator.getPendingRequestCount() > 0 && elevator.getState() != ElevatorState.MAINTENANCE) {
                elevator.processNextFloor();
            }
        }
    }

    public void setElevatorMaintenance(String elevatorId, boolean maintenance) {
        for (Elevator elevator : getAllElevators()) {
            if (elevator.getId().equals(elevatorId)) {
                if (maintenance) {
                    elevator.enterMaintenance();
                } else {
                    elevator.exitMaintenance();
                }
                return;
            }
        }
    }

    public void displayAllElevatorStatus() {
        System.out.println("\nBuilding: " + name + ", Floors: " + numFloors);
        System.out.println("Strategy: " + dispatcher.getStrategy().name());
        for (Elevator elevator : getAllElevators()) {
            System.out.println("  " + elevator);
        }
    }

    @Override
    public String toString() {
        return "Building{name='" + name + "', floors=" + numFloors + "}";
    }
}
