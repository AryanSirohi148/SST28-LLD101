

public class Snake implements BoardEntity {

    private final int head;
    private final int tail;

    public Snake(int head, int tail) {
        if (tail >= head)
            throw new IllegalArgumentException(
                "Snake tail (" + tail + ") must be < head (" + head + ")");
        this.head = head;
        this.tail = tail;
    }

    @Override public int resolve(int pos) { return tail; }
    @Override public EntityType getType() { return EntityType.SNAKE; }

    public int getHead() { return head; }
    public int getTail() { return tail; }

    @Override public String toString() { return "Snake[" + head + "->" + tail + "]"; }
}
