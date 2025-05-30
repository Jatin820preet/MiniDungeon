package dungeon.engine;

public class Gold extends CellLogic {

    public Gold(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }

    @Override
    public void interact(Player player) {
        player.addScore(2);
    }
}
