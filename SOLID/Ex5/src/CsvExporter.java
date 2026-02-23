import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        String body = req.body == null ? "" : req.body.replace("\n", " ").trim();
        String csv = "title,body\n" + req.title + "," + body;
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}