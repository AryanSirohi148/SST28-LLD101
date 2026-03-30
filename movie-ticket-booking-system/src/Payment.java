import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private final String id;
    private final String userId;
    private final double amount;
    private final PaymentMode mode;
    private final LocalDateTime createdAt;
    private PaymentStatus status;

    public Payment(String id, String userId, double amount, PaymentMode mode, PaymentStatus status) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.amount = amount;
        this.mode = Objects.requireNonNull(mode);
        this.status = Objects.requireNonNull(status);
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", amount=" + amount +
            ", mode=" + mode +
            ", status=" + status +
            '}';
    }
}
