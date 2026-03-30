import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Ticket {
    private final String id;
    private final String userId;
    private final String showId;
    private final List<String> seatIds;
    private final double totalAmount;
    private final String paymentId;
    private final PaymentMode paymentMode;
    private final TicketStatus status;
    private final LocalDateTime bookedAt;
    private final LocalDateTime cancelledAt;

    private Ticket(Builder builder) {
        this.id = Objects.requireNonNull(builder.id);
        this.userId = Objects.requireNonNull(builder.userId);
        this.showId = Objects.requireNonNull(builder.showId);
        this.seatIds = Collections.unmodifiableList(new ArrayList<>(builder.seatIds));
        this.totalAmount = builder.totalAmount;
        this.paymentId = Objects.requireNonNull(builder.paymentId);
        this.paymentMode = Objects.requireNonNull(builder.paymentMode);
        this.status = Objects.requireNonNull(builder.status);
        this.bookedAt = Objects.requireNonNull(builder.bookedAt);
        this.cancelledAt = builder.cancelledAt;
    }

    public static class Builder {
        private String id;
        private String userId;
        private String showId;
        private List<String> seatIds = new ArrayList<>();
        private double totalAmount;
        private String paymentId;
        private PaymentMode paymentMode;
        private TicketStatus status = TicketStatus.BOOKED;
        private LocalDateTime bookedAt = LocalDateTime.now();
        private LocalDateTime cancelledAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder showId(String showId) {
            this.showId = showId;
            return this;
        }

        public Builder seatIds(List<String> seatIds) {
            this.seatIds = new ArrayList<>(seatIds);
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder paymentMode(PaymentMode paymentMode) {
            this.paymentMode = paymentMode;
            return this;
        }

        public Builder status(TicketStatus status) {
            this.status = status;
            return this;
        }

        public Builder bookedAt(LocalDateTime bookedAt) {
            this.bookedAt = bookedAt;
            return this;
        }

        public Builder cancelledAt(LocalDateTime cancelledAt) {
            this.cancelledAt = cancelledAt;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    public Builder toBuilder() {
        return new Builder()
            .id(id)
            .userId(userId)
            .showId(showId)
            .seatIds(seatIds)
            .totalAmount(totalAmount)
            .paymentId(paymentId)
            .paymentMode(paymentMode)
            .status(status)
            .bookedAt(bookedAt)
            .cancelledAt(cancelledAt);
    }

    public Ticket withCancelledStatus() {
        return this.toBuilder()
            .status(TicketStatus.CANCELLED)
            .cancelledAt(LocalDateTime.now())
            .build();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", showId='" + showId + '\'' +
            ", seats=" + seatIds +
            ", amount=" + totalAmount +
            ", paymentMode=" + paymentMode +
            ", status=" + status +
            '}';
    }
}
