package object;

import javax.imageio.ImageIO;

public class ObjKey extends SuperObject {
    public ObjKey() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}