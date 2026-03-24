import java.util.List;
import java.util.TreeSet;

public class NearestSlotStrategy implements SlotFindingStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingLevel> levels, SlotType slotType, int gateNo) {
        ParkingSlot nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (ParkingLevel level : levels) {
            TreeSet<ParkingSlot> available = level.getAvailableSlots(slotType, gateNo);
            if (!available.isEmpty()) {
                ParkingSlot candidate = available.first();
                double dist = candidate.getDistanceFromGate(gateNo);
                if (dist < minDistance) {
                    minDistance = dist;
                    nearest = candidate;
                }
            }
        }
        return nearest;
    }
}
