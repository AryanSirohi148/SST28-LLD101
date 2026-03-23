public abstract class Pen {

    protected String color;

    protected InkRefillStrategy refillStrategy;
    protected StartStrategy startStrategy;

    protected boolean isStarted = false;

    public Pen(String color,
               InkRefillStrategy refillStrategy,
               StartStrategy startStrategy) {

        this.color = color;
        this.refillStrategy = refillStrategy;
        this.startStrategy = startStrategy;
    }

    // --- Core Methods ---

    public abstract void writeText(String text);

    public void refill(String newColor) {
        refillStrategy.refill(this, newColor);
    }

    public void open() {
        startStrategy.start(this);
        isStarted = true;
    }

    public void stop() {
        isStarted = false;
    }
}