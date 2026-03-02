import java.nio.charset.StandardCharsets;

public class CsvEncoder implements Encoder {

    @Override
    public ExportResult encode(ExportRequest req) {
        String safeTitle = escape(req.title);
        String safeBody = escape(req.body);

        String csv = safeTitle + "," + safeBody;

        return new ExportResult("text/csv",
                csv.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}
