import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AdminService {
    private final InMemoryStore store;
    private final PricingEngine pricingEngine;

    public AdminService(InMemoryStore store, PricingEngine pricingEngine) {
        this.store = store;
        this.pricingEngine = pricingEngine;
    }

    public Movie addMovie(String title, String language, int durationMins) {
        Movie movie = new Movie(IdGenerator.movieId(), title, language, durationMins);
        store.addMovie(movie);
        return movie;
    }

    public Theatre addTheatre(
        String name,
        String city,
        java.util.Map<SeatType, Double> basePriceBySeatType,
        List<Screen> screens
    ) {
        Theatre theatre = new Theatre(IdGenerator.theatreId(), name, city, basePriceBySeatType, screens);
        store.addTheatre(theatre);
        return theatre;
    }

    public Show addMovieShow(
        String movieId,
        String theatreId,
        String screenId,
        LocalDateTime startTime,
        double movieMultiplier,
        double slotMultiplier
    ) {
        Movie movie = store.getMovie(movieId);
        Theatre theatre = store.getTheatre(theatreId);

        if (movie == null) {
            throw new IllegalArgumentException("Movie not found: " + movieId);
        }
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre not found: " + theatreId);
        }

        Screen screen = theatre.getScreenById(screenId);
        if (screen == null) {
            throw new IllegalArgumentException("Screen not found in theatre: " + screenId);
        }

        Show show = new Show(
            IdGenerator.showId(),
            movie,
            theatre,
            screen,
            startTime,
            movieMultiplier,
            slotMultiplier
        );

        store.addShow(show);
        return show;
    }

    public void setRuntimePricingRules(List<String> selectedRuleCodes) {
        Objects.requireNonNull(selectedRuleCodes);
        pricingEngine.setActiveRules(PricingRuleFactory.createRules(selectedRuleCodes));
    }
}
