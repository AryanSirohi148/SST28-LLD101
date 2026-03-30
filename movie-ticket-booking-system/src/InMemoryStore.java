import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InMemoryStore {
    private static final InMemoryStore INSTANCE = new InMemoryStore();

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> userIdByEmail = new HashMap<>();
    private final Map<String, Movie> movies = new HashMap<>();
    private final Map<String, Theatre> theatres = new HashMap<>();
    private final Map<String, Show> shows = new HashMap<>();
    private final Map<String, Ticket> tickets = new HashMap<>();
    private final Map<String, Payment> payments = new HashMap<>();

    private InMemoryStore() {
    }

    public static InMemoryStore getInstance() {
        return INSTANCE;
    }

    public synchronized void addUser(User user) {
        String email = user.getEmail().toLowerCase();
        if (userIdByEmail.containsKey(email)) {
            throw new IllegalArgumentException("User already exists with email: " + email);
        }
        users.put(user.getId(), user);
        userIdByEmail.put(email, user.getId());
    }

    public synchronized User getUser(String userId) {
        return users.get(userId);
    }

    public synchronized void addMovie(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public synchronized Movie getMovie(String movieId) {
        return movies.get(movieId);
    }

    public synchronized void addTheatre(Theatre theatre) {
        theatres.put(theatre.getId(), theatre);
    }

    public synchronized Theatre getTheatre(String theatreId) {
        return theatres.get(theatreId);
    }

    public synchronized void addShow(Show show) {
        shows.put(show.getId(), show);
    }

    public synchronized Show getShow(String showId) {
        return shows.get(showId);
    }

    public synchronized void saveTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public synchronized Ticket getTicket(String ticketId) {
        return tickets.get(ticketId);
    }

    public synchronized void savePayment(Payment payment) {
        payments.put(payment.getId(), payment);
    }

    public synchronized Payment getPayment(String paymentId) {
        return payments.get(paymentId);
    }

    public synchronized List<Theatre> getTheatresByCity(String city) {
        String normalized = city.toLowerCase();
        List<Theatre> result = new ArrayList<>();
        for (Theatre theatre : theatres.values()) {
            if (theatre.getCity().equals(normalized)) {
                result.add(theatre);
            }
        }
        result.sort(Comparator.comparing(Theatre::getName));
        return result;
    }

    public synchronized List<Movie> getMoviesByCity(String city) {
        String normalized = city.toLowerCase();
        Set<String> movieIds = new HashSet<>();
        for (Show show : shows.values()) {
            if (show.getTheatre().getCity().equals(normalized)) {
                movieIds.add(show.getMovie().getId());
            }
        }

        List<Movie> result = new ArrayList<>();
        for (String movieId : movieIds) {
            result.add(movies.get(movieId));
        }
        result.sort(Comparator.comparing(Movie::getTitle));
        return result;
    }

    public synchronized List<Show> getShowsByMovieInCity(String movieId, String city) {
        String normalized = city.toLowerCase();
        List<Show> result = new ArrayList<>();

        for (Show show : shows.values()) {
            if (show.getMovie().getId().equals(movieId) && show.getTheatre().getCity().equals(normalized)) {
                result.add(show);
            }
        }

        result.sort(Comparator.comparing(Show::getStartTime));
        return result;
    }

    public synchronized List<Show> getShowsByTheatre(String theatreId) {
        List<Show> result = new ArrayList<>();

        for (Show show : shows.values()) {
            if (show.getTheatre().getId().equals(theatreId)) {
                result.add(show);
            }
        }

        result.sort(Comparator.comparing(Show::getStartTime));
        return result;
    }

    public synchronized List<Show> getAllShows() {
        List<Show> result = new ArrayList<>(shows.values());
        result.sort(Comparator.comparing(Show::getStartTime));
        return Collections.unmodifiableList(result);
    }
}
