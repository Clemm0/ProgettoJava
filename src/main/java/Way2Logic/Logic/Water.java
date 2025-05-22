package Way2Logic.Logic;

public class Water extends Block {
    public Water() {
        super("Water");
        sink = true;
        image = setImage(name);
    }
}
