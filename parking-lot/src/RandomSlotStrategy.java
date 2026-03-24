import java.util.List;
import java.util.TreeSet;

public class RandomSlotStrategy implements SlotFindingStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingLevel> levels, SlotType slotType, int gateNo) {
        for (ParkingLevel level : levels) {
            TreeSet<ParkingSlot> available = level.getAvailableSlots(slotType, gateNo);
            if (!available.isEmpty()) {
                int randomIndex = (int) (Math.random() * available.size());
                return available.stream().skip(randomIndex).findFirst().orElse(null);
            }
        }
        return null;
    }
}
