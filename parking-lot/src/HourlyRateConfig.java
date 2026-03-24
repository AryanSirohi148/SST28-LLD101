import java.util.HashMap;
import java.util.Map;

public class HourlyRateConfig {
    private Map<SlotType, Double> rates;

    public HourlyRateConfig() {
        this.rates = new HashMap<>();
    }

    public void setRate(SlotType slotType, double rate) {
        rates.put(slotType, rate);
    }

    public double getRate(SlotType slotType) {
        return rates.getOrDefault(slotType, 0.0);
    }

    public double calculateFee(SlotType slotType, double hours) {
        return getRate(slotType) * hours;
    }
}
