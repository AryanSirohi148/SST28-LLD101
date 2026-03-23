
import java.util.stream.Collectors;

public class TurnEventPrinter {

    public void print(TurnEvent e) {
        String name  = e.getPlayer().getName();
        String rolls = e.getRolls().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));

        switch (e.getType()) {
            case NORMAL:
                System.out.printf("%s rolled [%s] -> moved to %d%n",
                    name, rolls, e.getResolvedPosition());
                break;
            case SNAKE:
                System.out.printf("%s rolled [%s] -> landed %d, snake! -> %d%n",
                    name, rolls, e.getRawPosition(), e.getResolvedPosition());
                break;
            case LADDER:
                System.out.printf("%s rolled [%s] -> landed %d, ladder! -> %d%n",
                    name, rolls, e.getRawPosition(), e.getResolvedPosition());
                break;
            case BLOCKED:
                System.out.printf("%s rolled [%s] -> would overshoot, stays at %d%n",
                    name, rolls, e.getFromPosition());
                break;
            case SKIP:
                System.out.printf("%s rolled [%s] -> three sixes, turn skipped!%n",
                    name, rolls);
                break;
            case WIN:
                System.out.printf("%s rolled [%s] -> reached %d. %s WINS!%n",
                    name, rolls, e.getResolvedPosition(), name);
                break;
        }
    }
}
