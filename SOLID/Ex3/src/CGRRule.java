public class CGRRule implements EligibilityRule {
    private final double minCgr;

    public CGRRule(double minCgr) {
        this.minCgr = minCgr;
    }

    @Override
    public String check(StudentProfile student) {
        if (student.cgr < minCgr) {
            return "CGR below " + String.format("%.1f", minCgr);
        }
        return null;
    }
}
