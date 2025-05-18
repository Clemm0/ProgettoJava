package Main;

import object.ObjDoor;
import object.ObjFlag;
import object.ObjKey;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new ObjKey();
        gp.obj[0].worldX = 8 * gp.tileSize;
        gp.obj[0].worldY = 8 * gp.tileSize;

        gp.obj[1] = new ObjDoor();
        gp.obj[1].worldX = 3 * gp.tileSize;
        gp.obj[1].worldY = 4 * gp.tileSize;

        gp.obj[2] = new ObjFlag();
        gp.obj[2].worldX = 2 * gp.tileSize;
        gp.obj[2].worldY = 6 * gp.tileSize;
    }
}
