public class Floor {
    private final int floorNumber;
    private final String name;

    public Floor(int floorNumber, String name) {
        this.floorNumber = floorNumber;
        this.name = name;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Floor-" + floorNumber + "(" + name + ")";
    }
}
