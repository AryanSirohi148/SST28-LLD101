public class SimpleRefill implements InkRefillStrategy {

    public void refill(Pen pen, String color) {

        pen.color = color;

        System.out.println("Refilled pen with color: " + color);
    }
}