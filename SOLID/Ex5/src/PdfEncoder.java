import java.nio.charset.StandardCharsets;

public class PdfEncoder implements Encoder {
    @Override
    public ExportResult encode(ExportRequest req) {
        if (req.body != null && req.body.length() > 20) {
            String error = "ERROR: PDF cannot handle content > 20 chars";
            return new ExportResult("application/pdf",
                    error.getBytes(StandardCharsets.UTF_8));
        }

        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
