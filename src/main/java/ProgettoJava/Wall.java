package ProgettoJava;

public class Wall extends Immovable {

    public Wall(int x, int y) {
        super(x, y);
        setImage("src/main/resources/wall.png");
    }

    public boolean stopsPlayer() {
        return true;
    }

    public boolean stopsMovable() {
        return true;
    }
}