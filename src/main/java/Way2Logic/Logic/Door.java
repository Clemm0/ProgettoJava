package Way2Logic.Logic;

public class Door extends Block {
    public Door() {
        super("Door");
        collision = true;
        lock = true;
        image = setImage(name);
    }
}
