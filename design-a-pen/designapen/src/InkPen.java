public class InkPen extends Pen {

    public InkPen(String color,
                  InkRefillStrategy refillStrategy,
                  StartStrategy startStrategy) {
        super(color, refillStrategy, startStrategy);
    }

    @Override
    public void writeText(String text) {

        if(!isStarted) {
            throw new RuntimeException("Pen not started!");
        }

        System.out.println("InkPen writing: " + text);
    }
}