

import java.util.*;
public class BoardPrinter {

    private static final int CELL_W = 5;

    public void printBoard(Board board, List<Player> players) {
        int n = board.getSize();

        Map<Integer, String> tokens = buildTokenMap(players);

        String sep = "+" + ("------+").repeat(n);

        System.out.println(sep);

        for (int row = n - 1; row >= 0; row--) {
            StringBuilder numLine   = new StringBuilder("|");
            StringBuilder tokenLine = new StringBuilder("|");

            for (int col = 0; col < n; col++) {
                int cell = cellNumber(row, col, n);
                Cell c   = board.getCell(cell);

                String marker = "";
                if      (c.hasSnake())  marker = "S";
                else if (c.hasLadder()) marker = "L";

                String numStr = String.format("%3d%s", cell, marker);
                numLine.append(String.format(" %-4s |", numStr));

                String tok = tokens.getOrDefault(cell, "   ");
                tokenLine.append(String.format(" %-4s |", tok));
            }

            System.out.println(numLine);
            System.out.println(tokenLine);
            System.out.println(sep);
        }
    }

    public void printKey(Board board, List<Player> players) {
        System.out.println("Snakes (head->tail):");
        for (Cell c : board.getCells()) {
            if (c.hasSnake()) {
                Snake s = (Snake) c.getEntity().get();
                System.out.println("  " + s.getHead() + " -> " + s.getTail());
            }
        }
        System.out.println("Ladders (start->end):");
        for (Cell c : board.getCells()) {
            if (c.hasLadder()) {
                Ladder l = (Ladder) c.getEntity().get();
                System.out.println("  " + l.getStart() + " -> " + l.getEnd());
            }
        }
        System.out.println("Players:");
        String[] symbols = {"[A]","[B]","[C]","[D]","[E]","[F]"};
        for (int i = 0; i < players.size(); i++) {
            System.out.println("  " + symbols[i] + " " + players.get(i).getName());
        }
        System.out.println();
    }

    public void printPositions(List<Player> players) {
        System.out.println("Positions:");
        for (Player p : players) {
            String pos = p.getCurrentPosition() == 0 ? "start" : String.valueOf(p.getCurrentPosition());
            System.out.println("  " + p.getName() + ": " + pos);
        }
        System.out.println();
    }

    private int cellNumber(int row, int col, int n) {
        int base   = row * n;
        int offset = (row % 2 == 0) ? col : (n - 1 - col);
        return base + offset + 1;
    }

    private Map<Integer, String> buildTokenMap(List<Player> players) {
        Map<Integer, List<String>> raw = new LinkedHashMap<>();
        String[] symbols = {"A","B","C","D","E","F"};

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getCurrentPosition() > 0) {
                raw.computeIfAbsent(p.getCurrentPosition(), k -> new ArrayList<>())
                   .add(symbols[i]);
            }
        }

        Map<Integer, String> result = new HashMap<>();
        for (Map.Entry<Integer, List<String>> e : raw.entrySet()) {
            List<String> list = e.getValue();
            String label;
            if (list.size() <= 3) {
                label = String.join("", list);
            } else {
                label = list.get(0) + list.get(1) + "+";
            }
            result.put(e.getKey(), label);
        }
        return result;
    }
}
