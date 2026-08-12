package ui;

import model.Game;
import model.GameState;
import model.TileType;
import model.Position;
import model.Maze;
import model.ScoreManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Game game;
    private static final int TILE_SIZE = 30;

    public GamePanel(Game game) {
        this.game = game;
        int width = Maze.WIDTH * TILE_SIZE;
        int height = Maze.HEIGHT * TILE_SIZE;
        setPreferredSize(new Dimension(width, height + 50));
        setBackground(Color.BLACK);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawMaze(g2);
        drawEntities(g2);
        drawHUD(g2);
    }

    private void drawMaze(Graphics2D g) {
        for (int r = 0; r < Maze.HEIGHT; r++) {
            for (int c = 0; c < Maze.WIDTH; c++) {
                TileType tile = game.getMaze().getTile(c, r);
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;
                if (tile == TileType.WALL) {
                    g.setColor(new Color(33, 33, 255));
                    g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                } else if (tile == TileType.PELLET) {
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 12, y + 12, 6, 6);
                }
            }
        }
    }

    private void drawEntities(Graphics2D g) {
        Position pacPos = game.getPacman().getPosition();
        g.setColor(Color.YELLOW);
        g.fillOval(pacPos.x() * TILE_SIZE + 2, pacPos.y() * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4);

        game.getGhosts().forEach(ghost -> {
            g.setColor(Color.RED);
            Position gPos = ghost.getPosition();
            g.fillRoundRect(gPos.x() * TILE_SIZE + 2, gPos.y() * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4, 10, 10);
        });
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 15));

        int currentScore = game.getPacman().getScore();
        int highScore = ScoreManager.loadHighScore();

        String scoreInfo = "SCORE: " + currentScore + " | HIGH SCORE: " + highScore;
        g.drawString(scoreInfo, 10, getHeight() - 30);

        String status = "";
        if (game.getState() == GameState.READY) {
            status = "PRESS ENTER TO START";
        } else if (game.getState() == GameState.WON) {
            status = "YOU WIN! PRESS R TO RESTART";
        } else if (game.getState() == GameState.LOST) {
            status = "GAME OVER! PRESS R TO RESTART";
        }

        g.setColor(Color.YELLOW);
        g.drawString(status, 10, getHeight() - 10);
    }
}
