package Main;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tiles.TileManager;
import entity.Player;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    // Dimensione originale e scala per i tile
    final int originalSize = 16;
    final int scale = 4;
    public static int Size = 64;
    public final int tileSize = originalSize * scale;

    // Dimensioni dello schermo in tile (colonne e righe)
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    // Dimensioni dello schermo in pixel
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // FPS dalle impostazioni esterne
    public int FPS = Setting.getCurrentFPS();
    public static boolean optionState;

    // Dimensioni del mondo di gioco
    public final int maxWorldCol = maxScreenCol;
    public final int maxWorldRow = maxScreenRow;
    public final int maxWorldSize = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldRow;

    // Gestore dei tile
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public static String selectedCharacter;
    public Player player = new Player(this, keyH);
    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject[] obj = new SuperObject[10];

    Sound music = new Sound();
    Sound se = new Sound();

    private int drawCount = 0;

    // Riferimenti alla logica e mappa (modulo Way2Logic)
    public Way2Logic.Logic.Is isBlock;
    public String[][] map;
    public int isX, isY;

    public static GamePanel instance;

    // Costruttore che imposta la dimensione del pannello e inizializza il player
    public GamePanel(String selectedCharacter) {
        instance = this;
        if (selectedCharacter != null) {
            GamePanel.selectedCharacter = selectedCharacter.toLowerCase();
        } else {
            GamePanel.selectedCharacter = "cat";
        }
        player = new Player(this, keyH);
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(java.awt.Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Metodo per inizializzare il gioco
    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
    }

    // Avvia il thread di gioco che gestisce il ciclo principale
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Ciclo principale di gioco eseguito dal thread
    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread != null) {
            int currentFPS = getFPS();
            double drawInterval = 1000000000.0 / currentFPS;

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta = 0;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // Aggiorna tutti gli elementi del gioco
    public void update() {
        player.update();
        for (SuperObject o : obj) {
            if (o != null) {
                o.update();
            }
        }
        if (isBlock != null && map != null) {
            isBlock.updateLoc(map, isX, isY);
            isBlock.ChangeProperties();
        }
    }

    // Metodo di disegno chiamato automaticamente da repaint()
    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Centra la telecamera sul giocatore
        int cameraX = player.worldX - screenWidth / 2 + tileSize / 2;
        int cameraY = player.worldY - screenHeight / 2 + tileSize / 2;

        // Limita la telecamera per non mostrare fuori dalla mappa
        cameraX = Math.max(0, Math.min(cameraX, maxWorldSize - screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, maxWorldHeight - screenHeight));

        // Disegna i tile
        tileM.draw(g2, cameraX, cameraY);

        // Disegna gli oggetti
        for (SuperObject obj1 : obj) {
            if (obj1 != null) {
                obj1.draw(g2, this, cameraX, cameraY);
            }
        }

        // Disegna il giocatore
        player.draw(g2, cameraX, cameraY);

        g2.dispose();
    }

    // Riproduce la musica di sottofondo specificata dall'indice
    public void playMusic(int i) {
        music.setFile(i);
        float volume = 0.5f * (generalVolumeValue / 100f) * (musicVolumeValue / 100f); // Non funziona per impostare i valori
        music.setVolume(volume);
        music.play();
        music.loop();
    }

    // Ferma la musica di sottofondo
    public void stopMusic() {
        music.stop();
    }

    // Riproduce un effetto sonoro specificato dall'indice
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    // Restituisce gli FPS attuali dalle impostazioni
    public int getFPS() {
        return Setting.getCurrentFPS();
    }

    // Aggiorna il volume generale in tempo reale
    public static void updateGeneralVolume(int value) {
        generalVolumeValue = value;
        // Aggiorna sia la musica che gli effetti sonori con il nuovo master volume
        updateMusicVolume(musicVolumeValue);
        updateSfxVolume(sfxVolumeValue);
    }

    // Aggiorna il volume degli effetti sonori in tempo reale
    public static void updateSfxVolume(int value) {
        sfxVolumeValue = value;
        float volume = (generalVolumeValue / 100f) * (sfxVolumeValue / 100f); // QUI per SFX
        if (instance != null) {
            instance.se.setVolume(volume);
            // Se hai altri effetti sonori, aggiorna anche quelli
        }
    }

    // Aggiorna il volume della musica in tempo reale
    public static void updateMusicVolume(int value) {
        musicVolumeValue = value;
        float volume = (generalVolumeValue / 100f) * (musicVolumeValue / 100f); // QUI per musica
        if (instance != null) {
            instance.music.setVolume(volume);
        }
    }

    public static int generalVolumeValue = 100;
    public static int musicVolumeValue = 100;
    public static int sfxVolumeValue = 100;
}
