public class CardPaymentProcessor implements PaymentProcessor {
    @Override
    public Payment process(String paymentId, String userId, double amount) {
        return new Payment(paymentId, userId, amount, PaymentMode.CARD, PaymentStatus.SUCCESS);
    }

    @Override
    public PaymentStatus refund(Payment payment) {
        return PaymentStatus.REFUNDED;
    }
}
