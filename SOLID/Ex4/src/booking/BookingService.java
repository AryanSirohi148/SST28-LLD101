package booking;

import common.Money;
import pricing.HostelFeeCalculator;
import pricing.PriceComponentFactory;
import pricing.PricingComponent;
import receipt.ReceiptPrinter;

import java.util.List;

public class BookingService {
    BookingRepo repo;

    public BookingService(BookingRepo repo){
        this.repo = repo;
    }

    public void process(BookingRequest req) {
        List<PricingComponent> list = PriceComponentFactory.getComponent(req);
        Money monthly = HostelFeeCalculator.calculateMonthly(list);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = BookingId.generate();
        repo.save(bookingId, req, monthly, deposit);
    }
}
