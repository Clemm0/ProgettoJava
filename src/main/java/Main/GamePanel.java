package Main;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tiles.TileManager;
import entity.Player;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    // Dimensioni originali e scala per i tile
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

    // Frames per second (FPS) da impostazioni esterne
    public int FPS = Setting.getCurrentFPS();
    public static boolean optionState;

    // Dimensioni del mondo di gioco
    public final int maxWorldCol = maxScreenCol;
    public final int maxWorldRow = maxScreenRow;
    public final int maxWorldSize = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldRow;

    // Manager per il disegno dei tile
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH, "fox");
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

    // Costruttore che imposta la dimensione del pannello e inizializza il player
    public GamePanel(String selectedCharacter) {
        GamePanel.selectedCharacter = selectedCharacter.toLowerCase();
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

        // Center the camera on the player
        int cameraX = player.worldX - screenWidth / 2 + tileSize / 2;
        int cameraY = player.worldY - screenHeight / 2 + tileSize / 2;

        // Clamp camera so it doesn't show outside the map
        cameraX = Math.max(0, Math.min(cameraX, maxWorldSize - screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, maxWorldHeight - screenHeight));

        // Draw tiles
        tileM.draw(g2, cameraX, cameraY);

        // Draw objects
        for (SuperObject obj1 : obj) {
            if (obj1 != null) {
                obj1.draw(g2, this, cameraX, cameraY);
            }
        }

        // Draw player
        player.draw(g2, cameraX, cameraY);

        g2.dispose();
    }

    // Riproduce la musica di sottofondo specificata dall'indice
    public void playMusic(int i) {
        music.setFile(i);
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
}
