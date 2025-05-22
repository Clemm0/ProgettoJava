package object;

import javax.imageio.ImageIO;

public class ObjDoor2 extends SuperObject {

    // Costruttore della classe ObjDoor
    public ObjDoor2() {
        name = "Door2";
        try {
            // Carica l'immagine della porta dalla cartella delle risorse
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door2.png"));
        } catch (Exception e) {
            // In caso di errore durante il caricamento dell'immagine, stampa lo stack trace
            e.printStackTrace();
        }
    }
}