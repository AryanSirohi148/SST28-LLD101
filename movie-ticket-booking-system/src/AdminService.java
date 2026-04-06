import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

// handles admin operations like adding movies, theatres, shows
public class AdminService {
    private final InMemoryStore dataStore;
    private final PricingEngine pricingEngine;

    public AdminService(InMemoryStore dataStore, PricingEngine pricingEngine) {
        this.dataStore = dataStore;
        this.pricingEngine = pricingEngine;
    }

    public Movie addMovie(String name, String language, int durationMins) {
        Movie movie = new Movie(IdGenerator.movieId(), name, language, durationMins);
        dataStore.addMovie(movie);
        return movie;
    }

    public Theatre addTheatre(
        String name,
        String city,
        java.util.Map<SeatType, Double> basePriceBySeatType,
        List<Screen> screens
    ) {
        Theatre theatre = new Theatre(IdGenerator.theatreId(), name, city, basePriceBySeatType, screens);
        dataStore.addTheatre(theatre);
        return theatre;
    }

    public Show addMovieShow(
        String movieId,
        String theatreId,
        String screenId,
        LocalDateTime startTime,
        double movieFactor,
        double timeFactor
    ) {
        Movie movie = dataStore.getMovie(movieId);
        Theatre theatre = dataStore.getTheatre(theatreId);

        if (movie == null) {
            throw new IllegalArgumentException("Movie not found");
        }
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre not found");
        }

        Screen screen = theatre.getScreenById(screenId);
        if (screen == null) {
            throw new IllegalArgumentException("Screen not found");
        }

        Show show = new Show(
            IdGenerator.showId(),
            movie,
            theatre,
            screen,
            startTime,
            movieFactor,
            timeFactor
        );

        dataStore.addShow(show);
        return show;
    }

    public void setRuntimePricingRules(List<String> ruleCodes) {
        Objects.requireNonNull(ruleCodes);
        pricingEngine.setActiveRules(PricingRuleFactory.createRules(ruleCodes));
    }
}
