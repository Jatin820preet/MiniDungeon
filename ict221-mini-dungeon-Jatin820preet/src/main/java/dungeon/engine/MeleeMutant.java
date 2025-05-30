package dungeon.engine;

public class MeleeMutant extends CellLogic {

    public MeleeMutant(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }

    @Override
    public void interact(Player player) {
        player.takeDamage(2);
        player.addScore(2);
        // Mutant defeated logic can be handled in GameEngine (e.g., remove cell)
    }
}
