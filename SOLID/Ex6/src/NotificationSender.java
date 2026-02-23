// Base template for sending notifications.
// Defines common workflow (validation → normalization → send) to ensure LSP compliance
// also solve the problem by centering tightens precondition and loose postcondition problem
public abstract class NotificationSender {

    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    public final void send(Notification n) {
        if (!canSend(n)) {
            handleFailure(n);
            return;
        }
        String body = normalizeBody(n.body);
        doSend(n, body);
    }

    // Hook method for validation
    protected boolean canSend(Notification n) {
        return n != null;
    }

    // Hook method for failure behavior
    protected void handleFailure(Notification n) {
        // Default
    }

    // Default behavior: no change
    protected String normalizeBody(String body) {
        return body;
    }

    protected abstract void doSend(Notification n, String body);
}