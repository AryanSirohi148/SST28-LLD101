public class ExportResult {
    public final String contentType;
    public final byte[] bytes;
    public final boolean isSuccess;
    public final String errorMessage;

    private ExportResult(String contentType, byte[] bytes, boolean isSuccess, String errorMessage) {
        this.contentType = contentType;
        this.bytes = bytes;
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }

    public ExportResult(String contentType, byte[] bytes) {
        this(contentType, bytes, true, null);
    }

    public static ExportResult failure(String message) {
        return new ExportResult(null, null, false, message);
    }
}