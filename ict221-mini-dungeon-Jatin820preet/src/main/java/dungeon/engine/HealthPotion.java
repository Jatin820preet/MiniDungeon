package dungeon.engine;

public class HealthPotion extends CellLogic {

    public HealthPotion(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return 'H';
    }

    @Override
    public void interact(Player player) {
        player.heal(4); // restores HP up to max 10
    }
}
