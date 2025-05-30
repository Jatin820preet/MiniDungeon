package dungeon.engine;

public class Trap extends CellLogic {

    public Trap(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }

    @Override
    public void interact(Player player) {
        player.takeDamage(2);
    }
}
