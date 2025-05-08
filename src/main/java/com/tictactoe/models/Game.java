package com.tictactoe.models;

public class Game {
    private Player[] players;
    private char[][] board;
    private int currentPlayer;
    private int moveCount;
    private Database db;
    
    public Game(String player1Name, String player2Name) {
        players = new Player[2];
        players[0] = new Player(player1Name, 'X');
        players[1] = new Player(player2Name, 'O');
        board = new char[3][3];
        currentPlayer = 0;
        moveCount = 0;
        db = new Database();
    }
    
    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '\0') {
            return false;
        }
        
        board[row][col] = players[currentPlayer].getSymbol();
        moveCount++;
        return true;
    }
    
    public boolean checkWin() {
        char symbol = players[currentPlayer].getSymbol();
        
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }
        
        return false;
    }
    
    public boolean isDraw() {
        return moveCount == 9;
    }
    
    public void switchPlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }
    
    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }
    
    public void saveResult(String winner) {
        db.saveGameResult(players[0].getName(), players[1].getName(), 
                         winner, moveCount);
    }
    
    public void reset() {
        board = new char[3][3];
        currentPlayer = 0;
        moveCount = 0;
    }
}
