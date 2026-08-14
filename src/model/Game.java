package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Maze maze;
    private final Pacman pacman;
    private final List<Ghost> ghosts;
    private GameState state;

    public Game() {
        this.maze = new Maze();
        this.pacman = new Pacman(maze.getPacmanSpawn());
        this.ghosts = new ArrayList<>();
        ghosts.add(new Ghost(maze.getGhost1Spawn(), Direction.LEFT));
        ghosts.add(new Ghost(maze.getGhost2Spawn(), Direction.UP));
        this.state = GameState.READY;
    }

    public void start() {
        this.state = GameState.RUNNING;
    }

    public void update() {
        if (state != GameState.RUNNING) return;

        pacman.update(maze);
        for (Ghost ghost : ghosts) {
            ghost.update(maze);
        }

        checkCollisions();
        checkWinCondition();
    }

    public void handleInput(Direction newDirection) {
        if (state != GameState.RUNNING) return;
        pacman.setDirection(newDirection);
        pacman.addScore(-1);
    }

    private void checkCollisions() {
        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(pacman.getPosition())) {
                state = GameState.LOST;
                updateHighScore();
                return;
            }
        }
    }

    private void checkWinCondition() {
        if (maze.allPelletsCollected()) {
            pacman.addScore(500);
            state = GameState.WON;
            updateHighScore();
        }
    }

    private void updateHighScore() {
        int currentScore = pacman.getScore();
        int highScore = ScoreManager.loadHighScore();
        if (currentScore > highScore) {
            ScoreManager.saveHighScore(currentScore);
        }
    }

    public Maze getMaze() { return maze; }
    public Pacman getPacman() { return pacman; }
    public List<Ghost> getGhosts() { return ghosts; }
    public GameState getState() { return state; }
}
