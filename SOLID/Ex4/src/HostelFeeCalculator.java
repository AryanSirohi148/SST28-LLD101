import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;

    public HostelFeeCalculator(FakeBookingRepo repo) { this.repo = repo; }

    // OCP violation: switch + add-on branching + printing + persistence.
    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
            List<PricingComponent> components = new ArrayList<>();

            components.add(PricingFactory.roomOf(req.roomType));

            for (AddOn a : req.addOns) {
                components.add(PricingFactory.addOnOf(a));
            }

            Money total = new Money(0);

            for (PricingComponent c : components) {
                total = total.plus(c.cost());
            }

            return total;

    }
}
