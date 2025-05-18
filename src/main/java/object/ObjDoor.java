package object;

import javax.imageio.ImageIO;

public class ObjDoor extends SuperObject {
    public ObjDoor() {
        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (Exception e) {
            e.printStackTrace();
    }
}
}