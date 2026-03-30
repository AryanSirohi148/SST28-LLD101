public class PaymentProcessorFactory {

    public static PaymentProcessor create(PaymentMode mode) {
        switch (mode) {
            case UPI:
                return new UpiPaymentProcessor();
            case CARD:
                return new CardPaymentProcessor();
            case WALLET:
                return new WalletPaymentProcessor();
            default:
                throw new IllegalArgumentException("Unsupported payment mode: " + mode);
        }
    }
}
