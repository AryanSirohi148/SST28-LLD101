import java.util.List;

public class StudentPrinter {  //

    public void printErrors(List<String> errors){
        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors) System.out.println("- " + e);
        }
    }

    public void printSuccess(StudentRecord rec, int total){

        String id = rec.id;
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + total);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}
