package Way2Logic.Logic;

public class Flag extends Block {
    public Flag() {
        super("Flag");
        win = true;
        stop = true;
        image = setImage(name);
    }
}
