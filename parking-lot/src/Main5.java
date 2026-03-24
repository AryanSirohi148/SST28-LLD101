import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main5 {

    public static void main(String[] args) throws InterruptedException {

        // Creation ka implementation
        ParkingLot lot = ParkingLot.getInstance();

        EntryGate gate1 = new EntryGate(1);
        EntryGate gate2 = new EntryGate(2);
        List<Integer> gateNumbers = Arrays.asList(1, 2);

        ParkingSlot s1 = new ParkingSlot("L1-S1", SlotType.M);
        s1.setDistanceFromGate(1, 10.0);
        s1.setDistanceFromGate(2, 50.0);

        ParkingSlot s2 = new ParkingSlot("L1-S2", SlotType.M);
        s2.setDistanceFromGate(1, 20.0);
        s2.setDistanceFromGate(2, 40.0);

        ParkingSlot s3 = new ParkingSlot("L1-S3", SlotType.S);
        s3.setDistanceFromGate(1, 15.0);
        s3.setDistanceFromGate(2, 35.0);

        ParkingSlot s4 = new ParkingSlot("L2-S1", SlotType.M);
        s4.setDistanceFromGate(1, 25.0);
        s4.setDistanceFromGate(2, 20.0);

        ParkingSlot s5 = new ParkingSlot("L2-S2", SlotType.L);
        s5.setDistanceFromGate(1, 30.0);
        s5.setDistanceFromGate(2, 25.0);

        ParkingLevel level1 = new ParkingLevel(1);
        level1.addSlot(s1, gateNumbers);
        level1.addSlot(s2, gateNumbers);
        level1.addSlot(s3, gateNumbers);

        ParkingLevel level2 = new ParkingLevel(2);
        level2.addSlot(s4, gateNumbers);
        level2.addSlot(s5, gateNumbers);

        HourlyRateConfig rateConfig = new HourlyRateConfig();
        rateConfig.setRate(SlotType.S, 20.0);
        rateConfig.setRate(SlotType.M, 40.0);
        rateConfig.setRate(SlotType.L, 80.0);

        lot.setLevels(Arrays.asList(level1, level2));
        lot.setGates(Arrays.asList(gate1, gate2));
        lot.setRateConfig(rateConfig);
        lot.setStrategy(new NearestSlotStrategy());

        // Single vehicle test karenge

        System.out.println("=== Single Vehicle Test ===");
        VehicleDetail v1 = new VehicleDetail("MH01AB1234", VehicleType.CAR, "Arjun");
        ParkingReceipt receipt1 = lot.park(v1, LocalDateTime.now(), SlotType.M, 1);
        System.out.println(receipt1);

        VehicleDetail v2 = new VehicleDetail("MH02CD5678", VehicleType.TWO_WHEELER, "Riya");
        ParkingReceipt receipt2 = lot.park(v2, LocalDateTime.now(), SlotType.S, 2);
        System.out.println(receipt2);


        // sir ne concurrent vehicles ka bola tha
        System.out.println("\n=== Concurrent Vehicles Test (5 cars, gate 1) ===");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            int id = i;
            executor.submit(() -> {
                VehicleDetail v = new VehicleDetail(
                    "MH0" + id + "XX" + (1000 + id),
                    VehicleType.CAR,
                    "Driver-" + id
                );
                ParkingReceipt r = lot.park(v, LocalDateTime.now(), SlotType.M, 1);
                if (r != null) {
                    System.out.println("[Thread-" + id + "] Parked -> " + r.getAssignedSlot().getSlotId());
                } else {
                    System.out.println("[Thread-" + id + "] No slot available.");
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // exit vehicle bhi test kar lete hai
        System.out.println("\n=== Exit Test ===");
        lot.exit(receipt1);
        lot.exit(receipt2);
    }
}
