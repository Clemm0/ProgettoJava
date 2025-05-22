package object;

import javax.imageio.ImageIO;

public class ObjDoor3 extends SuperObject {

    // Costruttore della classe ObjDoor
    public ObjDoor3() {
        name = "Door3";
        try {
            // Carica l'immagine della porta dalla cartella delle risorse
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door3.png"));
        } catch (Exception e) {
            // In caso di errore durante il caricamento dell'immagine, stampa lo stack trace
            e.printStackTrace();
        }
    }
}