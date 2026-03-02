import javax.swing.*;

// Smell: unused UI wrapper
public class ConsoleUi implements Logger {
    @Override
    public void print(String s) { System.out.println(s); }
}
