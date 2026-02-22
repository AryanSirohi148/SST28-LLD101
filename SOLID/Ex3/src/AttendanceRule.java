import java.util.Optional;

public class AttendanceRule implements EligibilityRule{

    @Override
    public Optional<String> check(StudentProfile s){
        if(s.attendancePct < 75){
            return Optional.of("attendance below 75");
        }
        return Optional.empty();
    }
}
