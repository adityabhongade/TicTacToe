package com.tictactoe.models;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:tictactoe.db";
    
    public Database() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            String sql = "CREATE TABLE IF NOT EXISTS games (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "player1 TEXT NOT NULL," +
                         "player2 TEXT NOT NULL," +
                         "winner TEXT NOT NULL," +
                         "moves INTEGER NOT NULL," +
                         "game_date DATETIME DEFAULT CURRENT_TIMESTAMP)";
            
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void saveGameResult(String player1, String player2, String winner, int moves) {
        String sql = "INSERT INTO games(player1, player2, winner, moves) VALUES(?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, player1);
            pstmt.setString(2, player2);
            pstmt.setString(3, winner);
            pstmt.setInt(4, moves);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public ResultSet getGameStats() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM games ORDER BY game_date DESC");
    }
}
