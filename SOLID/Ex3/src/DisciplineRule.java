import java.util.Optional;

public class DisciplineRule implements EligibilityRule{

    public Optional<String> check(StudentProfile s){
        if(s.disciplinaryFlag != LegacyFlags.NONE){
            return Optional.of("disciplinary flag present");
        }
        return Optional.empty();
    }
}
