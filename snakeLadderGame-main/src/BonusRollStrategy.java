

import java.util.ArrayList;
import java.util.List;

public class BonusRollStrategy implements DiceStrategy {

    private static final int BONUS_VALUE   = 6;
    private static final int MAX_REROLLS   = 2;

    private final Dice dice;

    public BonusRollStrategy() {
        this.dice = new Dice();
    }

    @Override
    public RollResult roll() {
        List<Integer> rolls = new ArrayList<>();

        int value = dice.roll();
        rolls.add(value);

        while (value == BONUS_VALUE && rolls.size() <= MAX_REROLLS) {
            value = dice.roll();
            rolls.add(value);
        }

        if (rolls.size() == 3 && rolls.get(2) == BONUS_VALUE) {
            return new RollResult(rolls, 0, true);
        }

        int total = rolls.stream().mapToInt(Integer::intValue).sum();
        return new RollResult(rolls, total, false);
    }
}
