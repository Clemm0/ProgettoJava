package ProgettoJava;

public class Water extends Immovable {

    public Water(int x, int y) {
        super(x, y);
        setImage("src/main/resources/water.png");
    }

    public boolean sinksMovable() {
        return true;
    }

    public boolean stopsPlayer() {
        return true;
    }
}
