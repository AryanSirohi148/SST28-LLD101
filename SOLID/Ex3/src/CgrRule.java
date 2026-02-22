import java.util.Optional;

public class CgrRule implements EligibilityRule{

    @Override
    public Optional<String> check(StudentProfile s){
        if(s.cgr < 8.0){
            return Optional.of("CGR below 8.0");
        }
        return Optional.empty();
    }
}
