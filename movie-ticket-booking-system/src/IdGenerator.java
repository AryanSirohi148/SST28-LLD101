import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger USER = new AtomicInteger(1);
    private static final AtomicInteger MOVIE = new AtomicInteger(1);
    private static final AtomicInteger THEATRE = new AtomicInteger(1);
    private static final AtomicInteger SHOW = new AtomicInteger(1);
    private static final AtomicInteger TICKET = new AtomicInteger(1);

    private IdGenerator() {
    }

    public static String userId() {
        return "USR-" + USER.getAndIncrement();
    }

    public static String movieId() {
        return "MOV-" + MOVIE.getAndIncrement();
    }

    public static String theatreId() {
        return "TH-" + THEATRE.getAndIncrement();
    }

    public static String showId() {
        return "SH-" + SHOW.getAndIncrement();
    }

    public static String ticketId() {
        return "TKT-" + TICKET.getAndIncrement();
    }
}
