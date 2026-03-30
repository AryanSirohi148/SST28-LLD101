import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Screen {
    private final String id;
    private final String name;
    private final List<Seat> seats;
    private final Map<String, Seat> seatById;

    public Screen(String id, String name, List<Seat> seats) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.seats = Collections.unmodifiableList(new ArrayList<>(seats));

        Map<String, Seat> map = new LinkedHashMap<>();
        for (Seat seat : seats) {
            map.put(seat.getId(), seat);
        }
        this.seatById = Collections.unmodifiableMap(map);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Seat getSeatById(String seatId) {
        return seatById.get(seatId);
    }

    @Override
    public String toString() {
        return "Screen{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", seatCount=" + seats.size() +
            '}';
    }
}
