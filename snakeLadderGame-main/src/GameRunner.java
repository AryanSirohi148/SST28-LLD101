

import java.util.*;

public class GameRunner {

    private final Scanner          scanner  = new Scanner(System.in);
    private final BoardPrinter     boardPrinter = new BoardPrinter();
    private final TurnEventPrinter eventPrinter = new TurnEventPrinter();

    public void run() {
        System.out.println("=== Snake & Ladder ===");
        System.out.println();

        int n = readInt("Board size n (5-15): ", 5, 15);

        System.out.println();
        System.out.println("Difficulty:");
        System.out.println("  1. Easy - " + Difficulty.EASY.getDescription());
        System.out.println("  2. Hard - " + Difficulty.HARD.getDescription());
        int d = readInt("Choice (1/2): ", 1, 2);
        Difficulty difficulty = (d == 1) ? Difficulty.EASY : Difficulty.HARD;

        System.out.println();
        int count = readInt("Number of players (2-6): ", 2, 6);
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Name for player " + i + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = "Player " + i;
            players.add(new Player(name));
        }

        Board board = new BoardInitializer(n).initialize();
        Game  game  = new Game(board, players, difficulty.createStrategy());

        System.out.println();
        boardPrinter.printKey(board, players);
        boardPrinter.printBoard(board, players);

        waitEnter("Press Enter to start...");
        System.out.println();

        while (!game.isGameOver()) {
            Player cur = game.getCurrentPlayer();
            int pos = cur.getCurrentPosition();
            System.out.println("-- " + cur.getName()
                + "'s turn (at " + (pos == 0 ? "start" : pos) + ")");

            waitEnter("Press Enter to roll...");

            TurnEvent event = game.playTurn();
            eventPrinter.print(event);
            System.out.println();

            if (event.getType() == TurnEvent.Type.WIN) break;

            boardPrinter.printBoard(board, players);
            boardPrinter.printPositions(players);
        }

        GameResult r = game.getResult();
        if (r != null) {
            System.out.println("Game finished in " + r.getTotalTurns() + " turns.");
        }

        System.out.println();
        System.out.print("Play again? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println();
            run();
        } else {
            System.out.println("Thanks for playing.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    private void waitEnter(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }
}
