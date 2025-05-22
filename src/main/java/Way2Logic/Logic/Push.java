package Way2Logic.Logic;

public class Push extends Block {
    public Push() {
        super("Push");
        collision = true;
        push = true;
        image = setImage(name);
    }
}
