import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Show {
    private static class SeatLock {
        private final String userId;
        private final LocalDateTime lockedUntil;

        private SeatLock(String userId, LocalDateTime lockedUntil) {
            this.userId = userId;
            this.lockedUntil = lockedUntil;
        }
    }

    private final String id;
    private final Movie movie;
    private final Theatre theatre;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final double movieMultiplier;
    private final double slotMultiplier;

    private final Map<String, SeatState> seatStates;
    private final Map<String, SeatLock> seatLocks;

    public Show(
        String id,
        Movie movie,
        Theatre theatre,
        Screen screen,
        LocalDateTime startTime,
        double movieMultiplier,
        double slotMultiplier
    ) {
        this.id = Objects.requireNonNull(id);
        this.movie = Objects.requireNonNull(movie);
        this.theatre = Objects.requireNonNull(theatre);
        this.screen = Objects.requireNonNull(screen);
        this.startTime = Objects.requireNonNull(startTime);
        this.movieMultiplier = movieMultiplier;
        this.slotMultiplier = slotMultiplier;

        this.seatStates = new LinkedHashMap<>();
        this.seatLocks = new HashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatStates.put(seat.getId(), SeatState.AVAILABLE);
        }
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public double getMovieMultiplier() {
        return movieMultiplier;
    }

    public double getSlotMultiplier() {
        return slotMultiplier;
    }

    public synchronized Map<String, SeatState> getSeatMapSnapshot() {
        clearExpiredLocks();
        Map<String, SeatState> snapshot = new LinkedHashMap<>();
        for (Map.Entry<String, SeatState> entry : seatStates.entrySet()) {
            String seatId = entry.getKey();
            if (entry.getValue() == SeatState.BOOKED) {
                snapshot.put(seatId, SeatState.BOOKED);
            } else {
                SeatLock lock = seatLocks.get(seatId);
                snapshot.put(seatId, lock == null ? SeatState.AVAILABLE : SeatState.LOCKED);
            }
        }
        return snapshot;
    }

    public synchronized boolean lockSeats(String userId, List<String> seatIds, int lockSeconds) {
        clearExpiredLocks();

        for (String seatId : seatIds) {
            validateSeatId(seatId);
            if (seatStates.get(seatId) == SeatState.BOOKED) {
                return false;
            }
            SeatLock existingLock = seatLocks.get(seatId);
            if (existingLock != null && !existingLock.userId.equals(userId)) {
                return false;
            }
        }

        LocalDateTime lockUntil = LocalDateTime.now().plusSeconds(lockSeconds);
        for (String seatId : seatIds) {
            seatLocks.put(seatId, new SeatLock(userId, lockUntil));
        }
        return true;
    }

    public synchronized boolean confirmBooking(String userId, List<String> seatIds) {
        clearExpiredLocks();

        for (String seatId : seatIds) {
            SeatLock lock = seatLocks.get(seatId);
            if (lock == null || !lock.userId.equals(userId)) {
                return false;
            }
        }

        for (String seatId : seatIds) {
            seatStates.put(seatId, SeatState.BOOKED);
            seatLocks.remove(seatId);
        }
        return true;
    }

    public synchronized void releaseLocks(String userId, List<String> seatIds) {
        for (String seatId : seatIds) {
            SeatLock lock = seatLocks.get(seatId);
            if (lock != null && lock.userId.equals(userId)) {
                seatLocks.remove(seatId);
            }
        }
    }

    public synchronized void cancelBookedSeats(List<String> seatIds) {
        for (String seatId : seatIds) {
            if (seatStates.get(seatId) == SeatState.BOOKED) {
                seatStates.put(seatId, SeatState.AVAILABLE);
            }
        }
    }

    public synchronized int getTotalSeatCount() {
        return seatStates.size();
    }

    public synchronized int getBookedSeatCount() {
        int count = 0;
        for (SeatState state : seatStates.values()) {
            if (state == SeatState.BOOKED) {
                count++;
            }
        }
        return count;
    }

    public List<Seat> getSeats(List<String> seatIds) {
        List<Seat> result = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = screen.getSeatById(seatId);
            if (seat == null) {
                throw new IllegalArgumentException("Invalid seat id: " + seatId);
            }
            result.add(seat);
        }
        return Collections.unmodifiableList(result);
    }

    private void validateSeatId(String seatId) {
        if (screen.getSeatById(seatId) == null) {
            throw new IllegalArgumentException("Invalid seat id: " + seatId);
        }
    }

    private void clearExpiredLocks() {
        LocalDateTime now = LocalDateTime.now();
        List<String> expiredSeatIds = new ArrayList<>();
        for (Map.Entry<String, SeatLock> entry : seatLocks.entrySet()) {
            if (!entry.getValue().lockedUntil.isAfter(now)) {
                expiredSeatIds.add(entry.getKey());
            }
        }
        for (String seatId : expiredSeatIds) {
            seatLocks.remove(seatId);
        }
    }

    @Override
    public String toString() {
        return "Show{" +
            "id='" + id + '\'' +
            ", movie='" + movie.getTitle() + '\'' +
            ", theatre='" + theatre.getName() + '\'' +
            ", screen='" + screen.getName() + '\'' +
            ", startTime=" + startTime +
            '}';
    }
}
