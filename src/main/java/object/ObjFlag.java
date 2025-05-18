package object;

import javax.imageio.ImageIO;

public class ObjFlag extends SuperObject {
    public ObjFlag() {
        collision = false;
        name = "Flag";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/flag.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}