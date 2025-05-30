package dungeon.gui;

import javafx.scene.control.Label;
import dungeon.engine.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;


public class Controller {
    @FXML
    private GridPane gridPane;

    @FXML
    private Label hpLabel, scoreLabel, stepsLabel;

    private GameLogic gameLogic;
    private GameEngine engine;
    private boolean gameOver = false;


    @FXML
    public void initialize() {
        engine = new GameEngine(10); // GUI engine
        gameLogic = new GameLogic(); // actual logic
        updateGui();

        // Ensure gridPane can receive keyboard focus
        gridPane.setFocusTraversable(true);
        gridPane.requestFocus();

        // Keyboard movement handler
        gridPane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> moveUp();
                case A -> moveLeft();
                case S -> moveDown();
                case D -> moveRight();
                case Q -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quit");
                    alert.setHeaderText(null);
                    alert.setContentText("You quit the game.");
                    alert.showAndWait();

                    gameOver = true;
                    gridPane.setDisable(true);
                }

            }
        });
    }


    private void updateGui() {
        gridPane.getChildren().clear();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Rectangle tile = new Rectangle(40, 40);
                tile.setStroke(Color.BLACK);

                CellLogic cell = gameLogic.getCell(row, col);

                // Show player
                if (gameLogic.getPlayer().getRow() == row && gameLogic.getPlayer().getCol() == col) {
                    tile.setFill(Color.BLUE);
                }
                // Show traps
                else if (cell instanceof Trap) {
                    tile.setFill(Color.RED);
                }
                // Show gold
                else if (cell instanceof Gold) {
                    tile.setFill(Color.GOLD);
                }
                // Show health
                else if (cell instanceof HealthPotion) {
                    tile.setFill(Color.LIMEGREEN);
                }
                // Enemies
                else if (cell instanceof MeleeMutant) {
                    tile.setFill(Color.PURPLE);
                }
                else if (cell instanceof RangedMutant) {
                    tile.setFill(Color.ORANGE);
                }
                else if (cell instanceof Ladder) {
                    tile.setFill(Color.BROWN);
                }
                else if (cell instanceof Wall) {
                    tile.setFill(Color.GRAY);
                }

                else {
                    tile.setFill(Color.LIGHTGRAY); // empty cell
                }

                gridPane.add(tile, col, row);
            }
        }

        updateStats(); // refresh HP, score, steps
        checkGameOver();
    }

    private void updateStats() {
        hpLabel.setText("HP: " + gameLogic.getPlayer().getHp());
        scoreLabel.setText("Score: " + gameLogic.getPlayer().getScore());
        stepsLabel.setText("Steps: " + gameLogic.getPlayer().getSteps());
        checkGameOver();
    }
    private void checkGameOver() {
        if (!gameOver && !gameLogic.getPlayer().isAlive()) {
            gameOver = true;

            String cause = gameLogic.getPlayer().getHp() <= 0 ? "You died!" : "You ran out of steps!";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(cause + "\nFinal Score: " + gameLogic.getPlayer().getScore()
                    + "\nSteps Taken: " + gameLogic.getPlayer().getSteps());
            alert.showAndWait();

            gridPane.setDisable(true);
        }
    }

    private void checkWin() {
        if (!gameOver && gameLogic.isWinConditionMet()) {
            if (!gameLogic.isLevelTwo()) {
                gameLogic.nextLevel();
                updateGui(); // refresh for new level
            } else {
                gameOver = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You Win!");
                alert.setHeaderText(null);
                alert.setContentText("You escaped the final level!\nFinal Score: " + gameLogic.getPlayer().getScore()
                        + "\nSteps Taken: " + gameLogic.getPlayer().getSteps());
                alert.showAndWait();
                gridPane.setDisable(true);
            }
        }

    }

    public void moveUp() {
        if (gameOver) return;
        int r = gameLogic.getPlayer().getRow() - 1;
        int c = gameLogic.getPlayer().getCol();
        gameLogic.movePlayer(r, c);
        updateGui();
        checkGameOver();
        checkWin();
        gridPane.requestFocus();

    }

    public void moveDown() {
        if (gameOver) return;
        int r = gameLogic.getPlayer().getRow() + 1;
        int c = gameLogic.getPlayer().getCol();
        gameLogic.movePlayer(r, c);
        updateGui();
        checkGameOver();
        checkWin();
        gridPane.requestFocus();
    }

    public void moveLeft() {
        if (gameOver) return;
        int r = gameLogic.getPlayer().getRow();
        int c = gameLogic.getPlayer().getCol() - 1;
        gameLogic.movePlayer(r, c);
        updateGui();
        checkGameOver();
        checkWin();
        gridPane.requestFocus();

    }

    public void moveRight() {
        if (gameOver) return;
        int r = gameLogic.getPlayer().getRow();
        int c = gameLogic.getPlayer().getCol() + 1;
        gameLogic.movePlayer(r, c);
        updateGui();
        checkGameOver();
        checkWin();
        gridPane.requestFocus();
    }
}
