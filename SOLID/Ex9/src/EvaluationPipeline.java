public class EvaluationPipeline {
    Rubric rubric;
    PlagiarismService pc;
    Grader grader;
    ReportGenerator writer;
    Logger logger;

    EvaluationPipeline(PlagiarismService plag, Grader grader, ReportGenerator report, Logger logger){
         this.rubric = new Rubric();
         this.pc = plag;
         this.grader = grader;
         this.writer = report;
         this.logger = logger;
    }

    public void evaluate(Submission sub) {


        int plag = pc.check(sub);
        logger.log("PlagiarismScore=" + plag);

        int code = grader.grade(sub, rubric);
        logger.log("CodeScore=" + code);

        String reportName = writer.write(sub, plag, code);
        logger.log("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        logger.log("FINAL: " + result + " (total=" + total + ")");
    }
}
