package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[5];

    // Implementazione della musica
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
            setVolume(GamePanel.generalVolumeValue / 100f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per avviare la riproduzione del suono
    public void play() {
        clip.start();
        setVolume(GamePanel.generalVolumeValue / 100f); 
    }

    // Metodo per far riprodurre il suono in loop continuo
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        setVolume(GamePanel.generalVolumeValue / 100f);
    }

    // Metodo per fermare la riproduzione del suono
    public void stop() {
        clip.stop();
    }

    // Metodo per impostare il volume del suono
    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB;
            if (volume == 0f) {
                dB = gainControl.getMinimum();
            } else {
                dB = (float) (Math.log10(volume) * 20.0);
            }
            gainControl.setValue(dB);
        }
    }
}
