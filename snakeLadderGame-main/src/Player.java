

public class Player {

    private final String name;
    private int currentPosition;
    private boolean eliminated;

    public Player(String name) {
        this.name = name;
        this.currentPosition = 0;
    }

    public String getName()             { return name;            }
    public int getCurrentPosition()     { return currentPosition; }
    public boolean isEliminated()       { return eliminated;      }

    public void setCurrentPosition(int pos) { this.currentPosition = pos; }
    public void eliminate()                 { this.eliminated = true;     }

    public boolean hasWon(int totalCells)   { return currentPosition >= totalCells; }
}
