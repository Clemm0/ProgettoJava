package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[5];
//Implementazione della musica
// Costruttore della classe Sound
// Inizializza gli URL dei file audio da utilizzare
    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/bgm.wav");
        soundURL[1] = getClass().getResource("/res/sound/win.wav");
        soundURL[2] = getClass().getResource("/res/sound/door.wav");
        soundURL[3] = getClass().getResource("/res/sound/step.wav");
        soundURL[4] = getClass().getResource("/res/sound/splash.wav");
    }

    // Metodo per impostare il file audio da riprodurre, dato l'indice
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per avviare la riproduzione del suono
    public void play() {
        clip.start(); 
    }

    // Metodo per far riprodurre il suono in loop continuo
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Metodo per fermare la riproduzione del suono
    public void stop() {
        clip.stop();
    }
}
