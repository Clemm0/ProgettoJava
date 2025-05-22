package object;

import javax.imageio.ImageIO;

//Implementazione della Bandiera
public class ObjFlag extends SuperObject {

    public ObjFlag() {
        collision = false;
        name = "Flag";
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/res/objects/flag1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/flag2.png"));
            image = image1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}