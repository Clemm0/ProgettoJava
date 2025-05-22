package Way2Logic.Logic;

public class Key extends Block {
    public Key() {
        super("Key");
        collision = true;
        push = true;
        ulock = true;
        image = setImage(name);
    }
}
