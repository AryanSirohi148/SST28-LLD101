

import java.util.List;
import java.util.Collections;

public class TurnEvent {

    public enum Type { NORMAL, SNAKE, LADDER, BLOCKED, SKIP, WIN }

    private final Type type;
    private final Player player;
    private final List<Integer> rolls;
    private final int fromPosition;
    private final int rawPosition;
    private final int resolvedPosition;

    public TurnEvent(Type type, Player player, List<Integer> rolls,
                     int fromPosition, int rawPosition, int resolvedPosition) {
        this.type             = type;
        this.player           = player;
        this.rolls            = Collections.unmodifiableList(rolls);
        this.fromPosition     = fromPosition;
        this.rawPosition      = rawPosition;
        this.resolvedPosition = resolvedPosition;
    }

    public Type         getType()             { return type;             }
    public Player       getPlayer()           { return player;           }
    public List<Integer>getRolls()            { return rolls;            }
    public int          getFromPosition()     { return fromPosition;     }
    public int          getRawPosition()      { return rawPosition;      }
    public int          getResolvedPosition() { return resolvedPosition; }

    public int getTotalRoll() {
        return rawPosition - fromPosition;
    }
}
