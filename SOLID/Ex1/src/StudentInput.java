public class StudentInput {  //
    String name;
    String email;
    String phone;
    String program;

    public StudentInput(String name, String email, String phone, String program) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.program = program;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getProgram() {
        return program;
    }
}
