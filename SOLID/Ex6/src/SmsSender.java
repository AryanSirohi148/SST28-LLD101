// Sends notifications via SMS.
public class SmsSender extends NotificationSender {

    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    protected void doSend(Notification n, String body) {
        System.out.println(
                "SMS -> to=" + n.phone +
                        " body=" + body
        );
        audit.add("sms sent");
    }
}