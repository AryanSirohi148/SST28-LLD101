import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentValidator {  //

    public List<String> validate(StudentInput input){

        String name = input.getName();
        String email = input.getEmail();
        String phone = input.getPhone();
        String program = input.getProgram();

        List<String> errors = new ArrayList<>();
        if (name.isBlank()) errors.add("name is required");
        if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");

        return errors;
    }
}
