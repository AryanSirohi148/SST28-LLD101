import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MovieTicketSystem {
    private final InMemoryStore store;
    private final PricingEngine pricingEngine;
    private final AdminService adminService;
    private final SearchService searchService;
    private final BookingService bookingService;

    private final User admin;

    public MovieTicketSystem() {
        this.store = InMemoryStore.getInstance();
        this.pricingEngine = new PricingEngine();
        PaymentService paymentService = new PaymentService(store);
        BookingEventManager eventManager = new BookingEventManager();
        eventManager.register(new ConsoleBookingObserver());

        this.adminService = new AdminService(store, pricingEngine);
        this.searchService = new SearchService(store);
        this.bookingService = new BookingService(store, pricingEngine, paymentService, eventManager);

        this.admin = new User(IdGenerator.userId(), "SYSTEM_ADMIN", "admin@system.com", Role.ADMIN);
        store.addUser(admin);
    }

    public User getAdmin() {
        return admin;
    }

    public User registerCustomer(String name, String email) {
        User customer = new User(IdGenerator.userId(), name, email, Role.CUSTOMER);
        store.addUser(customer);
        return customer;
    }

    // Admin APIs
    public Movie addMovie(String title, String language, int durationMins) {
        return adminService.addMovie(title, language, durationMins);
    }

    public Theatre addTheatre(String name, String city, Map<SeatType, Double> basePriceBySeatType, List<Screen> screens) {
        return adminService.addTheatre(name, city, basePriceBySeatType, screens);
    }

    public Show addMovieShow(
        String movieId,
        String theatreId,
        String screenId,
        LocalDateTime startTime,
        double movieMultiplier,
        double slotMultiplier
    ) {
        return adminService.addMovieShow(movieId, theatreId, screenId, startTime, movieMultiplier, slotMultiplier);
    }

    public void setRuntimePricingRules(List<String> selectedRuleCodes) {
        adminService.setRuntimePricingRules(selectedRuleCodes);
    }

    // Search APIs
    public List<Theatre> showTheatre(String city) {
        return searchService.showTheatre(city);
    }

    public List<Movie> showMovies(String city) {
        return searchService.showMovies(city);
    }

    public List<Show> showTheatresWithSlotsForMovie(String city, String movieId) {
        return searchService.showTheatresWithSlotsForMovie(city, movieId);
    }

    public List<Show> showMoviesWithSlotsForTheatre(String theatreId) {
        return searchService.showMoviesWithSlotsForTheatre(theatreId);
    }

    // Booking APIs
    public Map<String, SeatState> showSeatMap(String showId) {
        return bookingService.showSeatMap(showId);
    }

    public double estimatePrice(String showId, List<String> seatIds) {
        return bookingService.estimateTotal(showId, seatIds);
    }

    public Ticket bookTickets(String userId, String showId, List<String> seatIds, PaymentMode mode) {
        return bookingService.bookTickets(userId, showId, seatIds, mode);
    }

    public Ticket cancelTicket(String userId, String ticketId) {
        return bookingService.cancelTicket(userId, ticketId);
    }
}
