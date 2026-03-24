public class VehicleDetail {
    private String licensePlate;
    private VehicleType vehicleType;
    private String ownerName;

    public VehicleDetail(String licensePlate, VehicleType vehicleType, String ownerName) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public String getOwnerName() { return ownerName; }

    @Override
    public String toString() {
        return ownerName + " | " + licensePlate + " | " + vehicleType;
    }
}
