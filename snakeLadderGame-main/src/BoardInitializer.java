

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BoardInitializer {

    private final int n;
    private final int total;
    private final Random random;

    public BoardInitializer(int n) {
        this.n      = n;
        this.total  = n * n;
        this.random = new Random();
    }

    public Board initialize() {
        Cell[] cells = new Cell[total];
        for (int i = 1; i <= total; i++) cells[i - 1] = new Cell(i);

        Set<Integer> occupied = new HashSet<>();
        occupied.add(1);
        occupied.add(total);

        placeSnakes(cells, occupied);
        placeLadders(cells, occupied);

        return new Board(n, cells);
    }

    private void placeSnakes(Cell[] cells, Set<Integer> occupied) {
        int placed = 0;
        for (int attempt = 0; attempt < 10_000 && placed < n; attempt++) {
            int head = randomBetween(3, total - 1);
            if (occupied.contains(head)) continue;
            int tail = randomBetween(1, head - 1);
            cells[head - 1].setEntity(new Snake(head, tail));
            occupied.add(head);
            placed++;
        }
    }

    private void placeLadders(Cell[] cells, Set<Integer> occupied) {
        int placed = 0;
        for (int attempt = 0; attempt < 10_000 && placed < n; attempt++) {
            int start = randomBetween(2, total - 2);
            if (occupied.contains(start)) continue;
            int end = randomBetween(start + 1, total - 1);
            if (occupied.contains(end)) continue;
            cells[start - 1].setEntity(new Ladder(start, end));
            occupied.add(start);
            placed++;
        }
    }

    private int randomBetween(int min, int max) {
        if (min > max) return min;
        return random.nextInt(max - min + 1) + min;
    }
}
