package model;

import java.util.Random;

public class Maze {
    public static final int WIDTH = 21;
    public static final int HEIGHT = 21;

    private final TileType[][] grid;
    private final Random random;

    private final Position pacmanSpawn = new Position(1, 1);
    private final Position ghost1Spawn = new Position(WIDTH - 2, 1);
    private final Position ghost2Spawn = new Position(WIDTH - 2, HEIGHT - 2);

    public Maze() {
        this.grid = new TileType[HEIGHT][WIDTH];
        this.random = new Random();
        generateRandomMaze();
    }

    public void generateRandomMaze(){
        for (int y =0; y < HEIGHT; y++){
            for (int x =0; x < WIDTH ; x++){
                if(y ==0 || x ==0 || y == HEIGHT-1 || x == WIDTH-1){
                    grid[y][x] = TileType.WALL;
                } else if (isSpawnCell(x,y)) {
                    grid[y][x] = TileType.EMPTY;
                }else {
                    if (grid[y - 1][x] == TileType.WALL && grid[y][x - 1] == TileType.WALL || grid[y + 1][x] == TileType.WALL && grid[y][x + 1] == TileType.WALL || grid[y + 1][x+1] == TileType.WALL && grid[y+1][x -1] == TileType.WALL || grid[y - 1][x-1] == TileType.WALL && grid[y+1][x + 1] == TileType.WALL) {
                        grid[y][x] = TileType.PELLET;
                    } else {
                        grid[y][x] = random.nextInt(100) < 15 ? TileType.WALL : TileType.PELLET;
                    }
                }
            }
        }
    }

    private boolean isSpawnCell(int x, int y) {
        return (x == pacmanSpawn.x() && y == pacmanSpawn.y())
                || (x == ghost1Spawn.x() && y == ghost1Spawn.y())
                || (x == ghost2Spawn.x() && y == ghost2Spawn.y());
    }


    public boolean isWalkable(Position pos) {
        if (pos == null) return false;
        int x = pos.x();
        int y = pos.y();
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return false;
        return grid[y][x] != TileType.WALL;
    }

    public boolean hasPellet(Position pos) {
        if (pos == null) return false;
        int x = pos.x();
        int y = pos.y();
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return false;
        return grid[y][x] == TileType.PELLET;
    }

    public void eatPellet(Position pos) {
        if (hasPellet(pos)) {
            grid[pos.y()][pos.x()] = TileType.EMPTY;
        }
    }

    public TileType getTile(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return TileType.WALL;
        return grid[y][x];
    }

    public Position getPacmanSpawn() {
        return pacmanSpawn;
    }

    public Position getGhost1Spawn() {
        return ghost1Spawn;
    }

    public Position getGhost2Spawn() {
        return ghost2Spawn;
    }

    public int remainingPellets() {
        int count = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == TileType.PELLET) count++;
            }
        }
        return count;
    }

    public boolean allPelletsCollected() {
        return remainingPellets() == 0;
    }


}
