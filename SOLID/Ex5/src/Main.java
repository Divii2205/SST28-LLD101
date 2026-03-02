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


// ----------
// ORIGINAL
// ----------

// public class Main {
//     public static void main(String[] args) {
//         System.out.println("=== Export Demo ===");

//         ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
//         Exporter pdf = new PdfExporter();
//         Exporter csv = new CsvExporter();
//         Exporter json = new JsonExporter();

//         System.out.println("PDF: " + safe(pdf, req));
//         System.out.println("CSV: " + safe(csv, req));
//         System.out.println("JSON: " + safe(json, req));
//     }

//     private static String safe(Exporter e, ExportRequest r) {
//         try {
//             ExportResult out = e.export(r);
//             return "OK bytes=" + out.bytes.length;
//         } catch (RuntimeException ex) {
//             return "ERROR: " + ex.getMessage();
//         }
//     }
// }

// Before the exporter excceptions had to be managed by try catch as some threw exceptions some didnt, no common behaviour 
// Now every exporter returns an output that can be checked for success or what kind of error
// The exporter class now handles the base behaviour in the same way for all child classes, and
// Subclasses only show format specific logic

// LSP => A subclass should not require more than what the base class requires
// In PdfExporter, it threw exceptions for large content
// But, from the base class perspective, large content was still a valid request
// So the subclass tightened the precondition, violating LSP

// JsonExporter
// It returned empty output for null, but PdfExporter threw exception
// That means each subclass handled invalid input differently