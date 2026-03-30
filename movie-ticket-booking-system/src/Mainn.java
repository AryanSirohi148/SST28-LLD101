import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Mainn {
    public static void main(String[] args) throws InterruptedException {
        MovieTicketSystem system = new MovieTicketSystem();

        // 1) Register users (duplicate email is blocked by design)
        User u1 = system.registerCustomer("Aarav", "aarav@mail.com");
        User u2 = system.registerCustomer("Diya", "diya@mail.com");

        // 2) Admin adds movie/theatre/show
        Movie movie1 = system.addMovie("Interstellar", "English", 169);
        Movie movie2 = system.addMovie("Kalki", "Hindi", 160);

        Map<SeatType, Double> basePrices = new EnumMap<>(SeatType.class);
        basePrices.put(SeatType.BRONZE, 200.0);
        basePrices.put(SeatType.SILVER, 350.0);
        basePrices.put(SeatType.GOLD, 600.0);
        basePrices.put(SeatType.DIAMOND, 1200.0);

        Screen screen1 = new Screen("SC-1", "Audi-1", SeatLayoutFactory.createSimpleLayout(
            new EnumMap<SeatType, Integer>(SeatType.class) {{
                put(SeatType.BRONZE, 8);
                put(SeatType.SILVER, 6);
                put(SeatType.GOLD, 4);
                put(SeatType.DIAMOND, 2);
            }}
        ));

        Screen screen2 = new Screen("SC-2", "Audi-2", SeatLayoutFactory.createSimpleLayout(
            new EnumMap<SeatType, Integer>(SeatType.class) {{
                put(SeatType.BRONZE, 6);
                put(SeatType.SILVER, 6);
                put(SeatType.GOLD, 4);
                put(SeatType.DIAMOND, 2);
            }}
        ));

        Theatre theatre = system.addTheatre("PVR Nexus", "Delhi", basePrices, Arrays.asList(screen1, screen2));

        LocalDateTime morningShowTime = LocalDateTime.of(2026, Month.APRIL, 5, 10, 0);
        LocalDateTime eveningShowTime = LocalDateTime.of(2026, Month.APRIL, 5, 18, 0);

        Show show1 = system.addMovieShow(movie1.getId(), theatre.getId(), "SC-1", morningShowTime, 1.10, 1.00);
        Show show2 = system.addMovieShow(movie2.getId(), theatre.getId(), "SC-1", eveningShowTime, 1.25, 1.20);

        // Runtime pricing rules selected by admin
        system.setRuntimePricingRules(Arrays.asList("WEEKEND_20", "DEMAND_80_50", "MONTH_PEAK"));

        // 3) Required discovery APIs
        System.out.println("\n--- showTheatre(Delhi) ---");
        System.out.println(system.showTheatre("Delhi"));

        System.out.println("\n--- showMovies(Delhi) ---");
        System.out.println(system.showMovies("Delhi"));

        System.out.println("\n--- showTheatresWithSlotsForMovie(Delhi, Interstellar) ---");
        System.out.println(system.showTheatresWithSlotsForMovie("Delhi", movie1.getId()));

        System.out.println("\n--- showMoviesWithSlotsForTheatre(PVR Nexus) ---");
        System.out.println(system.showMoviesWithSlotsForTheatre(theatre.getId()));

        // 4) Seat map
        System.out.println("\n--- Seat map before booking ---");
        System.out.println(system.showSeatMap(show1.getId()));

        // 5) Concurrency test: two users race for same seats
        List<String> targetSeats = Arrays.asList("B1", "S1", "G1");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                double estimate = system.estimatePrice(show1.getId(), targetSeats);
                System.out.println("User-1 estimate: " + estimate);
                Ticket ticket = system.bookTickets(u1.getId(), show1.getId(), targetSeats, PaymentMode.UPI);
                System.out.println("User-1 booked: " + ticket);
            } catch (Exception ex) {
                System.out.println("User-1 failed: " + ex.getMessage());
            }
        });

        executor.submit(() -> {
            try {
                double estimate = system.estimatePrice(show1.getId(), targetSeats);
                System.out.println("User-2 estimate: " + estimate);
                Ticket ticket = system.bookTickets(u2.getId(), show1.getId(), targetSeats, PaymentMode.CARD);
                System.out.println("User-2 booked: " + ticket);
            } catch (Exception ex) {
                System.out.println("User-2 failed: " + ex.getMessage());
            }
        });

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\n--- Seat map after concurrent attempt ---");
        System.out.println(system.showSeatMap(show1.getId()));

        // 6) Regular booking + cancellation with refund to original mode
        Ticket t2 = system.bookTickets(u2.getId(), show2.getId(), Arrays.asList("D1", "D2"), PaymentMode.WALLET);
        System.out.println("\nTicket booked for cancellation demo: " + t2);

        Ticket cancelled = system.cancelTicket(u2.getId(), t2.getId());
        System.out.println("Cancelled ticket: " + cancelled);

        System.out.println("\n--- Seat map after cancellation (show2) ---");
        System.out.println(system.showSeatMap(show2.getId()));
    }
}
