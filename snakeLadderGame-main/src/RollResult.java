

import java.util.List;
import java.util.Collections;

public class RollResult {

    private final List<Integer> rolls;
    private final int totalMove;
    private final boolean turnSkipped;

    public RollResult(List<Integer> rolls, int totalMove, boolean turnSkipped) {
        this.rolls       = Collections.unmodifiableList(rolls);
        this.totalMove   = totalMove;
        this.turnSkipped = turnSkipped;
    }

    public List<Integer> getRolls()       { return rolls;       }
    public int           getTotalMove()   { return totalMove;   }
    public boolean       isTurnSkipped()  { return turnSkipped; }
}
