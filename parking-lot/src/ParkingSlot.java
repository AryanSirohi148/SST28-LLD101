import java.util.HashMap;
import java.util.Map;

public class ParkingSlot {
    private String slotId;
    private SlotType slotType;
    private boolean isOccupied;
    private Map<Integer, Double> distanceFromGate;

    public ParkingSlot(String slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.isOccupied = false;
        this.distanceFromGate = new HashMap<>();
    }

    public void setDistanceFromGate(int gateNumber, double distance) {
        distanceFromGate.put(gateNumber, distance);
    }

    public double getDistanceFromGate(int gateNumber) {
        return distanceFromGate.getOrDefault(gateNumber, Double.MAX_VALUE);
    }

    public synchronized void occupy() {
        this.isOccupied = true;
    }

    public synchronized void vacate() {
        this.isOccupied = false;
    }

    public boolean isOccupied() { return isOccupied; }
    public String getSlotId() { return slotId; }
    public SlotType getSlotType() { return slotType; }

    @Override
    public String toString() {
        return slotId + " [" + slotType + "]";
    }
}
