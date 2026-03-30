import java.util.List;

public class SearchService {
    private final InMemoryStore store;

    public SearchService(InMemoryStore store) {
        this.store = store;
    }

    public List<Theatre> showTheatre(String city) {
        return store.getTheatresByCity(city);
    }

    public List<Movie> showMovies(String city) {
        return store.getMoviesByCity(city);
    }

    public List<Show> showTheatresWithSlotsForMovie(String city, String movieId) {
        return store.getShowsByMovieInCity(movieId, city);
    }

    public List<Show> showMoviesWithSlotsForTheatre(String theatreId) {
        return store.getShowsByTheatre(theatreId);
    }
}
