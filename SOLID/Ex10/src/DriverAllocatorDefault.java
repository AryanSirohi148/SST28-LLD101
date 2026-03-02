public class DriverAllocatorDefault implements DriverAllocator{
    @Override
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}
