package object;

import javax.imageio.ImageIO;

public class ObjDoor extends SuperObject {

    // Costruttore della classe ObjDoor
    public ObjDoor() {
        name = "Door";
        try {
            // Carica l'immagine della porta dalla cartella delle risorse
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (Exception e) {
            // In caso di errore durante il caricamento dell'immagine, stampa lo stack trace
            e.printStackTrace();
    }
}
}