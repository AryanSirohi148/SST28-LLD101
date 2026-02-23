// Sends notifications via WhatsApp.
// Validates phone format and handles channel-specific failure behavior.
public class WhatsAppSender extends NotificationSender {

    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected boolean canSend(Notification n) {
        return n.phone != null && n.phone.startsWith("+");
    }

    @Override
    protected void handleFailure(Notification n) {
        System.out.println("WA ERROR: phone must start with + and country code");
        audit.add("wa failed");
    }

    @Override
    protected void doSend(Notification n, String body) {
        System.out.println(
                "WA -> to=" + n.phone +
                        " body=" + body
        );
        audit.add("wa sent");
    }
}