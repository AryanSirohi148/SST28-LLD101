import java.util.List;

public interface ElevatorSchedulingStrategy {
    Elevator selectElevator(ElevatorRequest request, List<Elevator> availableElevators);

    String name();
}
