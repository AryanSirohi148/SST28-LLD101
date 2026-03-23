public class Main2 {
    public static void main(String[] args) {
        Pen pen = PenFactory.createPen("ink-pen", "blue", "with-cap");

        pen.open();
        pen.writeText("Mistakes are part of growth — what matters is learning from them and moving forward stronger.");
        pen.stop();

        pen.refill("black");
        // pen.write("will fail"); 

        Pen pen2 = PenFactory.createPen("ball-pen", "black", "click");

        pen2 = new GripDecorator(pen2);

        pen2.open();
        pen2.writeText("Nah I'd Win");
    }
}
