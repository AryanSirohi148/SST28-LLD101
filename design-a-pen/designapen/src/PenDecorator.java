public abstract class PenDecorator extends Pen {

    protected Pen pen;

    public PenDecorator(Pen pen) {
        super(pen.color, pen.refillStrategy, pen.startStrategy);
        this.pen = pen;
    }
}