public class EntryGate {
    private int gateNumber;
    private int floorLevel;

    public EntryGate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.floorLevel = 0;
    }

    public int getGateNumber() { return gateNumber; }
    public int getFloorLevel() { return floorLevel; }

    @Override
    public String toString() {
        return "Gate-" + gateNumber;
    }
}
