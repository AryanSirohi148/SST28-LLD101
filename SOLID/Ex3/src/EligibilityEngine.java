import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store,List<EligibilityRule> rules) {
        this.store = store;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s); // giant conditional inside
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for(EligibilityRule rule : rules){
            Optional<String> result = rule.check(s);
            if(result.isPresent()){
                reasons.add(result.get());
                break;
            }
        }

        if(!reasons.isEmpty()) status = "NOT_ELIGIBLE";
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
