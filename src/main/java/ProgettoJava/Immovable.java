package ProgettoJava;

public abstract class Immovable extends Placed {

    public Immovable(int x, int y) {
        super(x, y, false);
    }

    public boolean blocksMovement() {
        return true;
    }
}