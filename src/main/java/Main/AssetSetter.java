package Main;

import object.ObjDoor2;
import object.ObjFlag;
import object.ObjKey;

public class AssetSetter {
    GamePanel gp;

     // Costruttore che riceve il riferimento al GamePanel principale
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    // Metodo per posizionare gli oggetti nel mondo di gioco
    public void setObject() {

        // Posiziona la chiave all'interno del mondo (8,8)
        gp.obj[0] = new ObjKey();
        gp.obj[0].worldX = 8 * gp.tileSize;
        gp.obj[0].worldY = 8 * gp.tileSize;

        // Posiziona la porta all'interno del mondo (3,4)
        gp.obj[1] = new ObjDoor2();
        gp.obj[1].worldX = 3 * gp.tileSize;
        gp.obj[1].worldY = 4 * gp.tileSize;

        // Posiziona la bandiera all'interno del mondo (2,6)
        gp.obj[2] = new ObjFlag();
        gp.obj[2].worldX = 2 * gp.tileSize;
        gp.obj[2].worldY = 6 * gp.tileSize;

        // Aggiungere i blocchi per le connessioni di Way2Logic anche se non son completi quindi per sto update nulla
        // Nel prossimo update si usa un engune di base e si completa tutto
    }
}
