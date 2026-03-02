// Smell: unused console wrapper
public class SimpleConsole implements Logger{
    @Override
    public void log(String s) { System.out.println(s); }
}
