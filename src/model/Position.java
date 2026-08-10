package model;


import java.util.Objects;

public final class Position {
    private final int x;
    private final int y;

    public int x(){return x;}
    public int y(){return y;}

    public Position(int x, int y){
        this.x = x;
        this.y =y;
    }

    public Position move(Direction dr){
        return new Position(x + dr.dx,y +dr.dy);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Position)) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
