

public class GameResult {

    private final Player winner;
    private final int totalTurns;

    public GameResult(Player winner, int totalTurns) {
        this.winner     = winner;
        this.totalTurns = totalTurns;
    }

    public Player getWinner()     { return winner;     }
    public int getTotalTurns()    { return totalTurns; }
}
