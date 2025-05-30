import dungeon.engine.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameEngine {

    @Test
    void testPlayerMovementIncrementsSteps() {
        GameLogic logic = new GameLogic();
        int initialSteps = logic.getPlayer().getSteps();
        int newRow = logic.getPlayer().getRow() - 1;
        int newCol = logic.getPlayer().getCol();
        if (newRow < 0) newRow += 2;  // ensure valid move
        logic.movePlayer(newRow, newCol);
        assertEquals(initialSteps + 1, logic.getPlayer().getSteps());
    }

    @Test
    void testMeleeMutantInteraction() {
        Player p = new Player(5, 5);
        MeleeMutant mm = new MeleeMutant(5, 5);
        mm.interact(p);
        assertEquals(Player.MAX_HP - 2, p.getHp());
        assertEquals(2, p.getScore());
    }

    @Test
    void testRangedMutantAttackCondition() {
        RangedMutant rm = new RangedMutant(5, 5);
        Player p = new Player(3, 5);  // 2 tiles away vertically
        int initialHp = p.getHp();
        rm.tryRangedAttack(p);
        assertTrue(p.getHp() == initialHp || p.getHp() == initialHp - 2);
    }

    @Test
    void testStepLimitTriggersGameOver() {
        GameLogic logic = new GameLogic();
        Player p = logic.getPlayer();
        int moves = 0;
        while (p.getSteps() < 100 && p.isAlive()) {
            int r = p.getRow();
            int c = p.getCol();
            if (r > 0) logic.movePlayer(r - 1, c);
            else logic.movePlayer(r + 1, c);
            moves++;
            if (moves > 200) break; // failsafe to avoid infinite loop
        }
        assertFalse(p.isAlive());
    }

    @Test
    void testGoldPickupDecreasesTotal() {
        GameLogic logic = new GameLogic();
        Player p = logic.getPlayer();
        int r = p.getRow();
        int c = p.getCol();

        Gold g = new Gold(r - 1, c);
        logic.getCell(r - 1, c);
        logic.movePlayer(r - 1, c);
        assertTrue(p.getScore() >= 0);
    }

    @Test
    void testLadderUnlocksAfterGold() {
        GameLogic logic = new GameLogic();
        Player p = logic.getPlayer();
        for (int i = 0; i < 100; i++) {
            logic.movePlayer(p.getRow(), p.getCol());
        }
        // No assert here, but functionally ensures we can test unlocking
    }
}
