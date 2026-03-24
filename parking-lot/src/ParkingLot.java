import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private static volatile ParkingLot instance;

    private List<ParkingLevel> levels;
    private List<EntryGate> gates;
    private HourlyRateConfig rateConfig;
    private SlotFindingStrategy strategy;
    private boolean isInitialized;

    private ParkingLot() {
        this.levels = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.isInitialized = false;
    }

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public void setLevels(List<ParkingLevel> levels) {
        this.levels = levels;
    }

    public void setGates(List<EntryGate> gates) {
        this.gates = gates;
    }

    public void setRateConfig(HourlyRateConfig rateConfig) {
        this.rateConfig = rateConfig;
    }

    public void setStrategy(SlotFindingStrategy strategy) {
        this.strategy = strategy;
        this.isInitialized = true;
    }

    public ParkingReceipt park(VehicleDetail vehicleDetail, LocalDateTime entryTime,
                               SlotType slotType, int gateNo) {
        validateInitialized();

        while (true) {
            ParkingSlot slot = strategy.findSlot(levels, slotType, gateNo);

            if (slot == null) {
                System.out.println("No available slot of type " + slotType + " for gate " + gateNo);
                return null;
            }

            synchronized (slot) {
                if (slot.isOccupied()) {
                    continue;
                }
                slot.occupy();

                for (ParkingLevel level : levels) {
                    level.removeFromAvailable(slot, gateNo);
                }

                return new ParkingReceipt(vehicleDetail, slot, entryTime, slotType, gateNo);
            }
        }
    }

    public double exit(ParkingReceipt receipt) {
        validateInitialized();

        ParkingSlot slot = receipt.getAssignedSlot();
        LocalDateTime exitTime = LocalDateTime.now();
        double hours = Duration.between(receipt.getEntryTime(), exitTime).toMinutes() / 60.0;
        hours = Math.max(hours, 1.0);

        synchronized (slot) {
            slot.vacate();
            List<Integer> gateNumbers = new ArrayList<>();
            for (EntryGate gate : gates) {
                gateNumbers.add(gate.getGateNumber());
            }
            for (ParkingLevel level : levels) {
                level.addBackToAvailable(slot, gateNumbers);
            }
        }

        double fee = rateConfig.calculateFee(receipt.getSlotType(), hours);
        System.out.println("\n--- Exit ---");
        System.out.println("Receipt  : " + receipt.getReceiptId());
        System.out.println("Hours    : " + String.format("%.2f", hours));
        System.out.println("Fee      : Rs. " + String.format("%.2f", fee));
        System.out.println("------------");
        return fee;
    }

    private void validateInitialized() {
        if (!isInitialized || levels.isEmpty() || gates.isEmpty() || rateConfig == null) {
            throw new IllegalStateException("ParkingLot is not fully initialized. Call all setters first.");
        }
    }
}
