

public class Ladder implements BoardEntity {

    private final int start;
    private final int end;

    public Ladder(int start, int end) {
        if (end <= start)
            throw new IllegalArgumentException(
                "Ladder end (" + end + ") must be > start (" + start + ")");
        this.start = start;
        this.end   = end;
    }

    @Override public int resolve(int pos) { return end; }
    @Override public EntityType getType() { return EntityType.LADDER; }

    public int getStart() { return start; }
    public int getEnd()   { return end;   }

    @Override public String toString() { return "Ladder[" + start + "->" + end + "]"; }
}
