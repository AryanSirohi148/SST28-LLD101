import java.util.LinkedHashMap;
import java.util.Map;

public class StudentInputParser { // seprate class for parsing the data

    public StudentInput parse(String raw){

        Map<String,String> kv = new LinkedHashMap<>();
        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        StudentInput input = new StudentInput(name,email,phone,program); //instead of returning map directly, we return an structure object
        return input;
    }
}
