package ProgettoJava;

public abstract class Movable extends Placed {

    public Movable(int x, int y, boolean isMovable) {
        super(x, y, isMovable);
    }

    public boolean canMoveTo(int[][] map, int newX, int newY) {
        return map[newX][newY] == 0;
    }

    public void move(int[][] map, int newX, int newY) {
        if (canMoveTo(map, newX, newY)) {
            map[this.x][this.y] = 0;
            this.x = newX;
            this.y = newY;
            map[newX][newY] = 4;
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}