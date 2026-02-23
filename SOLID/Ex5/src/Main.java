import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        
        List<Exporter> exporters = List.of(new PdfExporter(), new CsvExporter(), new JsonExporter());
        String[] labels = {"PDF", "CSV", "JSON"};

        for (int i = 0; i < exporters.size(); i++) {
            ExportResult out = exporters.get(i).export(req);
            if (out.isSuccess) {
                System.out.println(labels[i] + ": OK bytes=" + out.bytes.length);
            } else {
                System.out.println(labels[i] + ": ERROR: " + out.errorMessage);
            }
        }
    }
}