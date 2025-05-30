package dungeon.engine;

public class Wall extends CellLogic {

    public Wall(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return '#';
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void interact(Player player) {
        // nothing happens
    }
}
