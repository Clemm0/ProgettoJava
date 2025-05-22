package object;

import javax.imageio.ImageIO;

//Implementazione della Bandiera
public class ObjFlag extends SuperObject {

    // Costruttore della bandiera
    public ObjFlag() {
        collision = false;
        name = "Flag";
        try {
            // Carica due immagini per l'animazione della bandiera (per effetto sventolio)
            image1 = ImageIO.read(getClass().getResourceAsStream("/res/objects/flag1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/flag2.png"));
            image = image1;
        } catch (Exception e) {

            // In caso di errore nel caricamento delle immagini, stampa lo stack trace
            e.printStackTrace();
        }
    }
}