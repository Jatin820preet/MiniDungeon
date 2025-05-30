// In Cell.java (pure logic)
package dungeon.engine;

public abstract class CellLogic {
    protected int row;
    protected int col;

    public CellLogic(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public abstract char getSymbol();
    public abstract void interact(Player player);
    public boolean isWalkable() {
        return true;
    }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

}
