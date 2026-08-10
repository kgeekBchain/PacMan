package model;

import java.util.Random;

public class Ghost extends Entity {
    private final Random random = new Random();

    public Ghost(Position spawnPosition, Direction initialDirection) {
        super(spawnPosition, initialDirection);
    }

    @Override
    public void update(Maze maze) {
        Position nextPosition = position.move(direction);


        if (!maze.isWalkable(nextPosition) || random.nextDouble() < 0.20) {
            Direction[] directions = Direction.values();
            Direction newDirection = directions[random.nextInt(directions.length)];

            Position candidatePos = position.move(newDirection);
            if (maze.isWalkable(candidatePos)) {
                this.direction = newDirection;
                this.position = candidatePos;
            }
        } else {
            this.position = nextPosition;
        }
    }
}
