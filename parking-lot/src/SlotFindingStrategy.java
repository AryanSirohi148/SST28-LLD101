import java.util.List;

public interface SlotFindingStrategy {
    ParkingSlot findSlot(List<ParkingLevel> levels, SlotType slotType, int gateNo);
}
