package object;

import javax.imageio.ImageIO;

public class ObjKey extends SuperObject {

    //Costruttore della chiave
    public ObjKey() {
        name = "Key";
        try {

            //Immagine della chiave
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
        } catch (Exception e) {
            // In caso di errore nel caricamento dell'immagine, stampa lo stack trace
            e.printStackTrace();
    }
}
}