package Main;

import javax.swing.JFrame;

public class Test {
    public static void main(String[] args) {
        
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);
        fr.setTitle("Test");

        GamePanel gamePanel = new GamePanel();
        fr.add(gamePanel);
        fr.pack();

        fr.setLocationRelativeTo(null);
        fr.setVisible(true);

        gamePanel.startGameThread();
    }
}
