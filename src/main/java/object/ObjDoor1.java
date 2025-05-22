package object;

import javax.imageio.ImageIO;

public class ObjDoor1 extends SuperObject {

    // Costruttore della classe ObjDoor
    public ObjDoor1() {
        name = "Door1";
        try {
            // Carica l'immagine della porta dalla cartella delle risorse
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door1.png"));
        } catch (Exception e) {
            // In caso di errore durante il caricamento dell'immagine, stampa lo stack trace
            e.printStackTrace();
        }
    }
}