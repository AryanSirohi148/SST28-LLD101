import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingReceipt {
    private String receiptId;
    private VehicleDetail vehicleDetail;
    private ParkingSlot assignedSlot;
    private LocalDateTime entryTime;
    private SlotType slotType;
    private int gateNumber;

    public ParkingReceipt(VehicleDetail vehicleDetail, ParkingSlot assignedSlot,
                          LocalDateTime entryTime, SlotType slotType, int gateNumber) {
        this.receiptId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicleDetail = vehicleDetail;
        this.assignedSlot = assignedSlot;
        this.entryTime = entryTime;
        this.slotType = slotType;
        this.gateNumber = gateNumber;
    }

    public String getReceiptId() { return receiptId; }
    public VehicleDetail getVehicleDetail() { return vehicleDetail; }
    public ParkingSlot getAssignedSlot() { return assignedSlot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public SlotType getSlotType() { return slotType; }
    public int getGateNumber() { return gateNumber; }

    @Override
    public String toString() {
        return "\n--- Receipt [" + receiptId + "] ---" +
               "\nVehicle  : " + vehicleDetail +
               "\nSlot     : " + assignedSlot +
               "\nEntry    : " + entryTime +
               "\nGate     : " + gateNumber +
               "\n-----------------------------";
    }
}
