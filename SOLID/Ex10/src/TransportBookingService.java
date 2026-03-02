public class TransportBookingService {
    DistanceCalculator dist;
    DriverAllocator alloc;
    PaymentGateway pay;
    Logger logger;
    PricingStrategy fareCalc;

    TransportBookingService(DistanceCalculator dist, DriverAllocator alloc, PaymentGateway pay,Logger logger,PricingStrategy fareCalc){
        this.dist = dist;
        this.alloc = alloc;
        this.pay = pay;
        this.logger = logger;
        this.fareCalc = fareCalc;
    }

    public void book(TripRequest req) {


        double km = dist.km(req.from, req.to);
        logger.print("DistanceKm=" + km);

        String driver = alloc.allocate(req.studentId);
        logger.print("Driver=" + driver);

        double fare = fareCalc.calculateFare(km);

        String txn = pay.charge(req.studentId, fare);
        logger.print("Payment=PAID txn=" + txn);

        BookingReceipt r = new BookingReceipt("R-501", fare);
        logger.print("RECEIPT: " + r.id + " | fare=" + String.format("%.2f", r.fare));
    }
}
