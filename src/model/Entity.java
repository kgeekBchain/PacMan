package model;

public abstract class Entity {
    protected Position position;
    protected Direction direction;

    public Entity(Position spawnPosition, Direction initialDirection){
        this.position = spawnPosition;
        this.direction = initialDirection;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }


    public abstract void update(Maze maze);
}
