import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PricingEngine {
    private List<PricingRule> activeRules = new ArrayList<>();

    public synchronized void setActiveRules(List<PricingRule> rules) {
        this.activeRules = new ArrayList<>(rules);
    }

    public synchronized List<PricingRule> getActiveRules() {
        return Collections.unmodifiableList(activeRules);
    }

    public double calculatePrice(Show show, Seat seat) {
        double basePrice = show.getTheatre().getBasePrice(seat.getSeatType());
        double currentPrice = basePrice * show.getMovieMultiplier() * show.getSlotMultiplier();

        PricingContext context = new PricingContext(
            show,
            seat,
            basePrice,
            currentPrice,
            show.getBookedSeatCount(),
            show.getTotalSeatCount(),
            LocalDateTime.now()
        );

        for (PricingRule rule : getActiveRules()) {
            currentPrice = rule.apply(currentPrice, context);
        }

        // Requirement: final seat price cannot go below theatre base seat price.
        currentPrice = Math.max(currentPrice, basePrice);
        return Math.round(currentPrice * 100.0) / 100.0;
    }
}
