

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Board board;
    private final List<Player> players;
    private final DiceStrategy diceStrategy;

    private int currentIndex;
    private int totalTurns;
    private boolean gameOver;
    private GameResult result;

    public Game(Board board, List<Player> players, DiceStrategy diceStrategy) {
        this.board        = board;
        this.players      = players;
        this.diceStrategy = diceStrategy;
        this.currentIndex = 0;
        this.totalTurns   = 0;
        this.gameOver     = false;
    }

    public boolean    isGameOver()   { return gameOver;  }
    public GameResult getResult()    { return result;    }
    public Board      getBoard()     { return board;     }
    public List<Player> getPlayers() { return players;   }
    public int        getTotalTurns(){ return totalTurns;}

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public TurnEvent playTurn() {
        if (gameOver) throw new IllegalStateException("Game is already over.");

        Player     player = getCurrentPlayer();
        RollResult rolled = diceStrategy.roll();
        int        from   = player.getCurrentPosition();
        totalTurns++;

        if (rolled.isTurnSkipped()) {
            advanceTurn();
            return new TurnEvent(
                TurnEvent.Type.SKIP, player, rolled.getRolls(), from, from, from);
        }

        int rawPos = from + rolled.getTotalMove();

        if (rawPos > board.getTotalCells()) {
            advanceTurn();
            return new TurnEvent(
                TurnEvent.Type.BLOCKED, player, rolled.getRolls(), from, rawPos, from);
        }

        int resolved = board.resolvePosition(rawPos);
        player.setCurrentPosition(resolved);

        TurnEvent.Type type;
        if      (resolved < rawPos) type = TurnEvent.Type.SNAKE;
        else if (resolved > rawPos) type = TurnEvent.Type.LADDER;
        else                        type = TurnEvent.Type.NORMAL;

        if (player.hasWon(board.getTotalCells())) {
            gameOver = true;
            result   = new GameResult(player, totalTurns);
            return new TurnEvent(
                TurnEvent.Type.WIN, player, rolled.getRolls(), from, rawPos, resolved);
        }

        advanceTurn();
        return new TurnEvent(type, player, rolled.getRolls(), from, rawPos, resolved);
    }

    public List<Player> activePlayers() {
        List<Player> active = new ArrayList<>();
        for (Player p : players)
            if (!p.isEliminated() && !p.hasWon(board.getTotalCells()))
                active.add(p);
        return active;
    }

    private void advanceTurn() {
        int tried = 0;
        do {
            currentIndex = (currentIndex + 1) % players.size();
            tried++;
        } while (tried < players.size() && shouldSkip(players.get(currentIndex)));
    }

    private boolean shouldSkip(Player p) {
        return p.isEliminated() || p.hasWon(board.getTotalCells());
    }
}
