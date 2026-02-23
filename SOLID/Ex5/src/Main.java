public class Main {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: " + safe(pdf.export(req)));
        System.out.println("CSV: " + safe(csv.export(req)));
        System.out.println("JSON: " + safe(json.export(req)));
    }

    private static String safe(ExportResult r) {
        if (!r.success) {
            return "ERROR: " + r.errorMessage;
        }
        return "OK bytes=" + r.bytes.length;
    }
}
