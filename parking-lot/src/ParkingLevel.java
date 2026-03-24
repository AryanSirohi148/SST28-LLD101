import java.util.*;

public class ParkingLevel {
    private int levelNumber;
    private Map<SlotType, List<ParkingSlot>> slots;
    private Map<SlotType, Map<Integer, TreeSet<ParkingSlot>>> availableSlotsByTypeAndGate;

    public ParkingLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        this.slots = new HashMap<>();
        this.availableSlotsByTypeAndGate = new HashMap<>();
        for (SlotType type : SlotType.values()) {
            slots.put(type, new ArrayList<>());
            availableSlotsByTypeAndGate.put(type, new HashMap<>());
        }
    }

    public void addSlot(ParkingSlot slot, List<Integer> gateNumbers) {
        slots.get(slot.getSlotType()).add(slot);
        Map<Integer, TreeSet<ParkingSlot>> gateMap = availableSlotsByTypeAndGate.get(slot.getSlotType());
        for (int gateNo : gateNumbers) {
            gateMap.computeIfAbsent(gateNo, k -> new TreeSet<>(
                Comparator.comparingDouble((ParkingSlot s) -> s.getDistanceFromGate(k))
                          .thenComparing(ParkingSlot::getSlotId)
            )).add(slot);
        }
    }

    public TreeSet<ParkingSlot> getAvailableSlots(SlotType type, int gateNo) {
        return availableSlotsByTypeAndGate
                .getOrDefault(type, Collections.emptyMap())
                .getOrDefault(gateNo, new TreeSet<>());
    }

    public void removeFromAvailable(ParkingSlot slot, int gateNo) {
        Map<Integer, TreeSet<ParkingSlot>> gateMap = availableSlotsByTypeAndGate.get(slot.getSlotType());
        if (gateMap != null && gateMap.containsKey(gateNo)) {
            gateMap.get(gateNo).remove(slot);
        }
    }

    public void addBackToAvailable(ParkingSlot slot, List<Integer> gateNumbers) {
        Map<Integer, TreeSet<ParkingSlot>> gateMap = availableSlotsByTypeAndGate.get(slot.getSlotType());
        for (int gateNo : gateNumbers) {
            if (gateMap.containsKey(gateNo)) {
                gateMap.get(gateNo).add(slot);
            }
        }
    }

    public int getLevelNumber() { return levelNumber; }

    public List<ParkingSlot> getSlotsByType(SlotType type) {
        return slots.getOrDefault(type, Collections.emptyList());
    }
}
