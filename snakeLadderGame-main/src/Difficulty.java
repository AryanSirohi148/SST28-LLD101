

public enum Difficulty {

    EASY("Normal rules — roll once, move that many cells."),
    HARD("Bonus roll on 6 — three sixes in a row skips your turn.");

    private final String description;

    Difficulty(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }

    public DiceStrategy createStrategy() {
        switch (this) {
            case EASY: return new NormalDiceStrategy();
            case HARD: return new BonusRollStrategy();
            default:   throw new IllegalStateException("Unknown difficulty: " + this);
        }
    }
}
