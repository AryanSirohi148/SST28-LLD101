public class WalletPaymentProcessor implements PaymentProcessor {
    @Override
    public Payment process(String paymentId, String userId, double amount) {
        return new Payment(paymentId, userId, amount, PaymentMode.WALLET, PaymentStatus.SUCCESS);
    }

    @Override
    public PaymentStatus refund(Payment payment) {
        return PaymentStatus.REFUNDED;
    }
}
