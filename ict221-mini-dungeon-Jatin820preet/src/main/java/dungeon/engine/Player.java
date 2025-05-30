package dungeon.engine;

public class Player {
    private int row;
    private int col;
    private int hp;
    private int score;
    private int steps;

    public static final int MAX_HP = 10;

    public Player(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
        this.hp = MAX_HP;
        this.score = 0;
        this.steps = 0;
    }

    // Movement
    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
        this.steps++;
    }

    // HP control
    public void takeDamage(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    public void heal(int amount) {
        this.hp = Math.min(MAX_HP, this.hp + amount);
    }

    // Score
    public void addScore(int amount) {
        this.score += amount;
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHp() { return hp; }
    public int getScore() { return score; }
    public int getSteps() { return steps; }

    // Status check
    public boolean isAlive() {
        return hp > 0 && alive;
    }
    public boolean alive = true;

    public void setAlive(boolean status) {
        this.alive = status;
    }



}
