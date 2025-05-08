package com.tictactoe.controllers;

import com.tictactoe.models.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    @FXML private GridPane gameBoard;
    @FXML private Label statusLabel;
    
    private Game game;
    private Button[][] buttons = new Button[3][3];
    
    public void initialize() {
        game = new Game("Player 1", "Player 2");
        setupBoard();
        updateStatus();
    }
    
    private void setupBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                button.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
                
                final int r = row;
                final int c = col;
                button.setOnAction(e -> handleButtonClick(r, c));
                
                gameBoard.add(button, col, row);
                buttons[row][col] = button;
            }
        }
    }
    
    private void handleButtonClick(int row, int col) {
        if (game.makeMove(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getCurrentPlayer().getSymbol()));
            
            if (game.checkWin()) {
                statusLabel.setText(game.getCurrentPlayer().getName() + " wins!");
                game.saveResult(game.getCurrentPlayer().getName());
                disableAllButtons();
            } else if (game.isDraw()) {
                statusLabel.setText("It's a draw!");
                game.saveResult("Draw");
            } else {
                game.switchPlayer();
                updateStatus();
            }
        }
    }
    
    private void updateStatus() {
        statusLabel.setText(game.getCurrentPlayer().getName() + "'s turn");
    }
    
    private void disableAllButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
    }
    
    @FXML
    private void resetGame() {
        game.reset();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setDisable(false);
            }
        }
        updateStatus();
    }
    
    @FXML
    private void showStats() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tictactoe/views/StatsView.fxml"));
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setTitle("Game Statistics");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
