package model;

public class Pacman extends Entity{
    private int score;

    public Pacman(Position spawnPosition){
        super(spawnPosition,Direction.RIGHT);
        this.score =0;
    }

    @Override
    public void update(Maze maze){
        Position nextPosition = position.move(direction);

        if (maze.isWalkable(nextPosition)) {
            this.position = nextPosition;

            if (maze.hasPellet(this.position)) {
                maze.eatPellet(this.position);
                this.score += 10;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}
