public interface PaymentGateway {
    public String charge(String studentId, double amount);
}
