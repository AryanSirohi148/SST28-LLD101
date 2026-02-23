// Sends notifications via Email.
// Applies email-specific body normalization (e.g., truncation) before sending.

public class EmailSender extends NotificationSender {

    public EmailSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected String normalizeBody(String body) {
        if (body.length() > 40)
            return body.substring(0, 40);
        return body;
    }

    @Override
    protected void doSend(Notification n, String body) {
        System.out.println(
                "EMAIL -> to=" + n.email +
                        " subject=" + n.subject +
                        " body=" + body
        );
        audit.add("email sent");
    }
}