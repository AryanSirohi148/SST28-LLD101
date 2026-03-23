

import java.util.Optional;

public class Cell {

    private final int cellNumber;
    private BoardEntity entity;

    public Cell(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public int getCellNumber()             { return cellNumber;              }
    public void setEntity(BoardEntity e)   { this.entity = e;               }
    public Optional<BoardEntity> getEntity(){ return Optional.ofNullable(entity); }
    public boolean hasSnake()  { return entity != null && entity.getType() == EntityType.SNAKE;  }
    public boolean hasLadder() { return entity != null && entity.getType() == EntityType.LADDER; }
}
