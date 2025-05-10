package ProgettoJava;

public class Key extends Movable {

    public Key(int x, int y) {
        super(x, y, true);
        setImage("src/main/resources/key.png");
    }

    public void interactWithDoor(Door door) {
        if (door.isLocked()) {
            door.unlock();
        }
    }
}