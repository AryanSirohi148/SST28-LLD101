public class Main6 {
    public static void main(String[] args) {
        System.out.println("===== Elevator System Demo =====\n");

        // 1) Create building with ShortestPathFirst strategy
        Building building = new Building("Main Building", 5, new ShortestPathFirstStrategy());
        building.addElevator("E1");
        building.addElevator("E2");

        System.out.println("\nBuilding created: " + building);
        building.displayAllElevatorStatus();

        // 2) Demo: User from Floor 0 presses UP button, user weight 70kg
        System.out.println("\n--- Scenario 1: User at Ground Floor presses UP ---");
        OutsidePanel panel0 = building.getOutsidePanel(0);
        panel0.pressButton(ButtonType.UP, building.getDispatcher(), 70.0);

        // Process elevator movement
        building.processAllElevators();

        // 3) User inside elevator selects Floor 3
        System.out.println("\n--- Scenario 2: User inside elevator selects Floor 3 ---");
        InsidePanel insidePanel = building.getInsidePanel("E1");
        insidePanel.pressButton(ButtonType.FLOOR, 3, building.getAllElevators().get(0));

        // Process multiple steps of elevator movement
        for (int step = 0; step < 4; step++) {
            building.processAllElevators();
        }

        // 4) Demo: Test weight limit
        System.out.println("\n--- Scenario 3: User tries to enter with weight > 700kg ---");
        try {
            OutsidePanel floor2Panel = building.getOutsidePanel(2);
            floor2Panel.pressButton(ButtonType.DOWN, building.getDispatcher(), 750.0);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        // 5) Demo: Maintenance mode
        System.out.println("\n--- Scenario 4: E1 enters maintenance ---");
        building.setElevatorMaintenance("E1", true);
        building.displayAllElevatorStatus();

        System.out.println("\n--- E1 exits maintenance ---");
        building.setElevatorMaintenance("E1", false);

        // 6) Demo: Multiple concurrent requests
        System.out.println("\n--- Scenario 5: Multiple requests (FCFS strategy) ---");
        Building fcfsBuilding = new Building("Office", 4, new FirstComeFirstServeStrategy());
        fcfsBuilding.addElevator("E3");

        OutsidePanel p0 = fcfsBuilding.getOutsidePanel(0);
        OutsidePanel p1 = fcfsBuilding.getOutsidePanel(1);
        OutsidePanel p2 = fcfsBuilding.getOutsidePanel(2);

        p0.pressButton(ButtonType.UP, fcfsBuilding.getDispatcher(), 60.0);
        p1.pressButton(ButtonType.UP, fcfsBuilding.getDispatcher(), 70.0);
        p2.pressButton(ButtonType.DOWN, fcfsBuilding.getDispatcher(), 80.0);

        fcfsBuilding.displayAllElevatorStatus();

        // Process and show all movements
        for (int i = 0; i < 8; i++) {
            fcfsBuilding.processAllElevators();
        }

        // 7) Demo: Alarm button
        System.out.println("\n--- Scenario 6: Emergency alarm triggered ---");
        Elevator e3 = fcfsBuilding.getAllElevators().get(0);
        System.out.println("  Current state: " + e3);
        e3.triggerAlarm();
        System.out.println("  After alarm: " + e3);

        // 8) Final status
        System.out.println("\n--- Final Status ---");
        building.displayAllElevatorStatus();
        fcfsBuilding.displayAllElevatorStatus();

        System.out.println("\n===== End =====");
    }
}
