package dungeon.engine;

import java.util.Random;

public class RangedMutant extends CellLogic {

    private static final Random rand = new Random();

    public RangedMutant(int row, int col) {
        super(row, col);
    }

    @Override
    public char getSymbol() {
        return 'R';
    }

    @Override
    public void interact(Player player) {
        // If player steps on the tile directly
        player.addScore(2);
    }


    // For ranged attack from 2 tiles away
    public void tryRangedAttack(Player player) {
        boolean hit = rand.nextDouble() < 0.5;
        if (hit) {
            player.takeDamage(2);
            // "Ranged mutant attacked and hit!"
        } else {
            // "Ranged mutant attacked but missed."
        }
    }
}
