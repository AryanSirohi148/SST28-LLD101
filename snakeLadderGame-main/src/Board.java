public class Board {

    private final int size;
    private final int totalCells;
    private final Cell[] cells;

    public Board(int size, Cell[] cells) {
        this.size       = size;
        this.totalCells = size * size;
        this.cells      = cells;
    }

    public int resolvePosition(int position) {
        if (position < 1 || position > totalCells) return position;
        return cells[position - 1].getEntity()
                                   .map(e -> e.resolve(position))
                                   .orElse(position);
    }

    public Cell getCell(int position) { return cells[position - 1]; }
    public int  getSize()             { return size;                }
    public int  getTotalCells()       { return totalCells;          }
    public Cell[] getCells()          { return cells;               }
}
