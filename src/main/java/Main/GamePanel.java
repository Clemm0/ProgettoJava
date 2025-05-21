package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    // Tile settings
    final int originalSize = 16; // 16x16 tile originale
    final int scale = 4; // scala 4x
    public final int tileSize = originalSize * scale; // 64 pixel per tile

    // Screen settings
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 px
    public final int screenHeight = tileSize * maxScreenRow; // 768 px

    // World settings (la mappa 16x12 tile come hai passato)
    public final int maxWorldCol = 16;
    public final int maxWorldRow = 12;
    public final int worldWidth = tileSize * maxWorldCol; // 1024 px
    public final int worldHeight = tileSize * maxWorldRow; // 768 px

    // FPS
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    TileManager tileM = new TileManager(this);
    Player player = new Player(this, keyH);

    // Posizione camera nel mondo
    int cameraX, cameraY;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        // Posizione iniziale player (per esempio centro mappa)
        player.setWorldPosition(tileSize * 8, tileSize * 6);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();

        // Calcola posizione camera centrata sul player
        cameraX = player.worldX - screenWidth / 2;
        cameraY = player.worldY - screenHeight / 2;

        // Limita camera ai bordi della mappa per non vedere nero attorno
        if (cameraX < 0) cameraX = 0;
        if (cameraY < 0) cameraY = 0;
        if (cameraX > worldWidth - screenWidth) cameraX = worldWidth - screenWidth;
        if (cameraY > worldHeight - screenHeight) cameraY = worldHeight - screenHeight;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Trasla il contesto grafico per simulare la camera
        g2.translate(-cameraX, -cameraY);

        // Disegna la mappa
        tileM.draw(g2);

        // Disegna il player usando coordinate mondo
        player.draw(g2);

        // Torna indietro (opzionale)
        g2.translate(cameraX, cameraY);

        g2.dispose();
    }
}
