public abstract class Exporter {
    // implied "contract" but not enforced (smell)
    public ExportResult export(ExportRequest req) {
        if (req == null) {
            return ExportResult.error("request is null");
        }
        return doExport(req);
    }
    
    protected abstract ExportResult doExport(ExportRequest req);
}
