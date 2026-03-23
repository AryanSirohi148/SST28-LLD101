public class GripDecorator extends PenDecorator {

    public GripDecorator(Pen pen) {
        super(pen);
    }

    @Override
    public void open() {
        pen.open();
    }

    @Override
    public void writeText(String text) {

        System.out.println("Writing with better grip...");

        pen.writeText(text);
    }
}