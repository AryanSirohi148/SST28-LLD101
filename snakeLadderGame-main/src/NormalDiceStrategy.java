

import java.util.Collections;

public class NormalDiceStrategy implements DiceStrategy {

    private final Dice dice;

    public NormalDiceStrategy() {
        this.dice = new Dice();
    }

    @Override
    public RollResult roll() {
        int value = dice.roll();
        return new RollResult(
            Collections.singletonList(value),
            value,
            false
        );
    }
}
