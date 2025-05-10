package ProgettoJava;

public class Door extends Immovable {
    private boolean locked = true;

    public Door(int x, int y) {
        super(x, y);
        setImage("src/main/resources/door.png");
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
        setImage("src/main/resources/unlocked_door.png");
    }
}
