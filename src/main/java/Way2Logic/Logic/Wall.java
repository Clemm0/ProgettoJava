package Way2Logic.Logic;

public class Wall extends Block {
    public Wall() {
        super("Wall");
        collision = true;
        push = false;
        stop = true;
        image = setImage(name);
    }
}
