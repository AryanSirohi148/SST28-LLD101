import java.time.LocalDateTime;

public class PricingContext {
    private final Show show;
    private final Seat seat;
    private final double basePrice;
    private final double movieAndSlotPrice;
    private final int bookedSeatCount;
    private final int totalSeatCount;
    private final LocalDateTime now;

    public PricingContext(
        Show show,
        Seat seat,
        double basePrice,
        double movieAndSlotPrice,
        int bookedSeatCount,
        int totalSeatCount,
        LocalDateTime now
    ) {
        this.show = show;
        this.seat = seat;
        this.basePrice = basePrice;
        this.movieAndSlotPrice = movieAndSlotPrice;
        this.bookedSeatCount = bookedSeatCount;
        this.totalSeatCount = totalSeatCount;
        this.now = now;
    }

    public Show getShow() {
        return show;
    }

    public Seat getSeat() {
        return seat;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getMovieAndSlotPrice() {
        return movieAndSlotPrice;
    }

    public int getBookedSeatCount() {
        return bookedSeatCount;
    }

    public int getTotalSeatCount() {
        return totalSeatCount;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public double getFillRatio() {
        if (totalSeatCount == 0) {
            return 0.0;
        }
        return (double) bookedSeatCount / totalSeatCount;
    }
}
