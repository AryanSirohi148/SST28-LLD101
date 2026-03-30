import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Theatre {
    private final String id;
    private final String name;
    private final String city;
    private final Map<SeatType, Double> basePriceBySeatType;
    private final List<Screen> screens;

    public Theatre(
        String id,
        String name,
        String city,
        Map<SeatType, Double> basePriceBySeatType,
        List<Screen> screens
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city).toLowerCase();

        Map<SeatType, Double> copy = new EnumMap<>(SeatType.class);
        copy.putAll(Objects.requireNonNull(basePriceBySeatType));
        this.basePriceBySeatType = Collections.unmodifiableMap(copy);

        this.screens = Collections.unmodifiableList(new ArrayList<>(screens));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public double getBasePrice(SeatType seatType) {
        Double value = basePriceBySeatType.get(seatType);
        if (value == null) {
            throw new IllegalArgumentException("Base price not configured for " + seatType);
        }
        return value;
    }

    public Screen getScreenById(String screenId) {
        for (Screen screen : screens) {
            if (screen.getId().equals(screenId)) {
                return screen;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Theatre{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", city='" + city + '\'' +
            ", screens=" + screens.size() +
            '}';
    }
}
