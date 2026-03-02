import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store, List<EligibilityRule> rules) {
        this.store = store;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (EligibilityRule rule : rules) {
            String reason = rule.check(s);
            if (reason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(reason);
                break; // stop at first failure (same as the old if-else)
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;

    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}


// import java.util.*;

// public class EligibilityEngine {
//     private final FakeEligibilityStore store;

//     public EligibilityEngine(FakeEligibilityStore store) { this.store = store; }

//     public void runAndPrint(StudentProfile s) {
//         ReportPrinter p = new ReportPrinter();
//         EligibilityEngineResult r = evaluate(s); // giant conditional inside
//         p.print(s, r);
//         store.save(s.rollNo, r.status);
//     }

//     public EligibilityEngineResult evaluate(StudentProfile s) {
//         List<String> reasons = new ArrayList<>();
//         String status = "ELIGIBLE";

//         // OCP violation: long chain for each rule
//         if (s.disciplinaryFlag != LegacyFlags.NONE) {
//             status = "NOT_ELIGIBLE";
//             reasons.add("disciplinary flag present");
//         } else if (s.cgr < 8.0) {
//             status = "NOT_ELIGIBLE";
//             reasons.add("CGR below 8.0");
//         } else if (s.attendancePct < 75) {
//             status = "NOT_ELIGIBLE";
//             reasons.add("attendance below 75");
//         } else if (s.earnedCredits < 20) {
//             status = "NOT_ELIGIBLE";
//             reasons.add("credits below 20");
//         }

//         return new EligibilityEngineResult(status, reasons);
//     }
// }

// class EligibilityEngineResult {
//     public final String status;
//     public final List<String> reasons;
//     public EligibilityEngineResult(String status, List<String> reasons) {
//         this.status = status;
//         this.reasons = reasons;
//     }
// }

// Created seperate interface EligibilityRule implemented by
// Credits, Eligibility, CGR and Attendance classes