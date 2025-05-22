package Main;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tiles.TileManager;
import entity.Player;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {
    final int originalSize = 16;
    final int scale = 4;
    public final int tileSize = originalSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int FPS = Setting.getCurrentFPS();
    public static boolean optionState;
    public final int maxWorldCol = maxScreenCol;
    public final int maxWorldRow = maxScreenRow;
    public final int maxWorldSize = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldRow;
    private int frameCounter = 0;
    private final int switchFrames = 30;

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

    public GamePanel(String selectedCharacter) {
        GamePanel.selectedCharacter = selectedCharacter.toLowerCase();
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(java.awt.Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread != null) {
            // Always get the latest FPS value
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

    public void update() {
        player.update();
        for (SuperObject o : obj) {
            if (o != null) {
                o.update();
            }
        }
    }

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

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public int getFPS() {
        return Setting.getCurrentFPS();
    }
}
