import java.util.*;

public class OnboardingService {
    private final StudentRepository db;

    public OnboardingService(StudentRepository db) { this.db = db; }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        StudentInputParser parser = new StudentInputParser();
        StudentInput input = parser.parse(raw);                //parsing the raw data

        StudentValidator validator = new StudentValidator();
        List<String> errors = validator.validate(input);        //validate the input


        StudentPrinter printer = new StudentPrinter();           //print error if any
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }


        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, input.getName(), input.getEmail(), input.getPhone(), input.getProgram());



        db.save(rec);
        int total = db.count();




        printer.printSuccess(rec,total);
    }
}
