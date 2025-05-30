package dungeon.engine;

public class Ladder extends CellLogic {
    public Ladder(int row, int col) {
        super(row, col);
    }

    @Override
    public void interact(Player player) {
        // no effect on stats — game ends when stepped on in Controller
    }

    @Override
    public char getSymbol() {
        return 'L';
    }


}
