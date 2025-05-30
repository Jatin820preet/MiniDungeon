package dungeon.engine;
import java.util.Scanner;

import java.util.Random;

public class GameLogic {
    private static final int SIZE = 10;
    private CellLogic[][] logicMap = new CellLogic[SIZE][SIZE];
    private Player player;
    private int totalGold = 0;
    private boolean ladderUnlocked = false;
    private int ladderRow = 0;
    private int ladderCol = 9; // or any random cell
    private boolean alive = true; // make sure this field exists




    public GameLogic() {
        player = new Player(SIZE - 1, 0);
        placeItems();
    }

    private void placeItems() {

        for (int i = 0; i < 5; i++) {
            placeRandom(new Trap(0, 0));
            placeRandom(new Gold(0, 0));
            totalGold++;
        }
        for (int i = 0; i < 2; i++) {
            placeRandom(new HealthPotion(0, 0));
        }
        // Add a few walls
        for (int i = 0; i < 8; i++) {
            placeRandom(new Wall(0, 0));
        }

        placeRandom(new MeleeMutant(0, 0));
        placeRandom(new RangedMutant(0, 0));
    }

    private void placeRandom(CellLogic item) {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(SIZE);
            col = rand.nextInt(SIZE);
        } while (logicMap[row][col] != null || (row == SIZE - 1 && col == 0)); // skip start

        item.setPosition(row, col);  // Add a setPosition() method if needed
        logicMap[row][col] = item;
    }
    private boolean levelTwo = false;

    public boolean isLevelTwo() {
        return levelTwo;
    }

    public void nextLevel() {
        levelTwo = true;
        ladderUnlocked = false;
        logicMap = new CellLogic[SIZE][SIZE];
        player.moveTo(SIZE - 1, 0);
        placeItems(); // Place new items for level 2
    }
    private void handleRangedAttacks() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                CellLogic cell = logicMap[row][col];
                if (cell instanceof RangedMutant) {
                    RangedMutant mutant = (RangedMutant) cell;
                    int pr = player.getRow();
                    int pc = player.getCol();

                    // Ranged attack if player is 2 tiles away in a straight line
                    if ((Math.abs(row - pr) == 2 && col == pc) || (Math.abs(col - pc) == 2 && row == pr)) {
                        mutant.tryRangedAttack(player);
                    }
                }
            }
        }
    }



    public void movePlayer(int newRow, int newCol) {
        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) return;
        if (!player.isAlive()) return;

        CellLogic cell = logicMap[newRow][newCol];

        // Block movement into walls or other non-walkable cells
        if (cell != null && !cell.isWalkable()) return;

        player.moveTo(newRow, newCol);
        if (player.getSteps() >= 100) {

            player.setAlive(false);
        }



        // Interact with the cell
        if (cell != null) {
            cell.interact(player);

            // If it's gold, decrease total gold and possibly spawn ladder
            if (cell instanceof Gold) {
                totalGold--;
                if (totalGold == 0 && !ladderUnlocked) {
                    ladderUnlocked = true;
                    logicMap[ladderRow][ladderCol] = new Ladder(ladderRow, ladderCol);
                }
            }

            // Clear any cell except the ladder (we want ladder to remain visible)
            if (!(cell instanceof Ladder)) {
                logicMap[newRow][newCol] = null;
            }
        }

        // Handle any ranged mutant attacks after moving
        handleRangedAttacks();
    }


    public void printMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (player.getRow() == i && player.getCol() == j) {
                    System.out.print("P ");
                } else if (logicMap[i][j] != null) {
                    System.out.print(logicMap[i][j].getSymbol() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
    public CellLogic getCell(int row, int col) {



        return logicMap[row][col];
    }
    public boolean isWinConditionMet() {
        return getCell(player.getRow(), player.getCol()) instanceof Ladder;
    }


    public Player getPlayer() {
        return player;
    }
    public static void main(String[] args) {
        GameLogic game = new GameLogic();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MiniDungeon (Text Version)!");
        System.out.println("Use W/A/S/D to move. Try to collect gold and survive!");

        while (game.getPlayer().isAlive()) {
            game.printMap();
            System.out.println("HP: " + game.getPlayer().getHp() +
                    ", Score: " + game.getPlayer().getScore() +
                    ", Steps: " + game.getPlayer().getSteps());

            System.out.print("Move (w/a/s/d): ");
            String move = scanner.nextLine().trim().toLowerCase();

            int r = game.getPlayer().getRow();
            int c = game.getPlayer().getCol();

            switch (move) {
                case "w" -> game.movePlayer(r - 1, c); // up
                case "s" -> game.movePlayer(r + 1, c); // down
                case "a" -> game.movePlayer(r, c - 1); // left
                case "d" -> game.movePlayer(r, c + 1); // right
                case "q" -> {
                    System.out.println("You quit the game.");
                    return;
                }
                default -> System.out.println("Invalid input. Use W/A/S/D.");
            }
        }


        System.out.println("Game Over!");
        System.out.println("Final Score: " + game.getPlayer().getScore());
        System.out.println("Steps Taken: " + game.getPlayer().getSteps());
    }


}
