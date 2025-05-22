package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JSlider volumeSlider;
    private String selectedCharacter = null;
    private final Font retroFont = new Font("Monospaced", Font.BOLD, 18);
    private final Color fgColor = new Color(0, 255, 0);

    // Main Menu del gioco
    public MainMenu() {
        setTitle("Game Main Menu");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createMainMenuPanel(), "main");

        add(cardPanel);
        setVisible(true);
    }

    // Pannello per il menu principale
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(20, 20, 20));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        // Titolo del gioco
        JLabel title = new JLabel("Mystic Fur-Venture", JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 48));
        title.setForeground(fgColor);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int stringWidth = metrics.stringWidth(title.getText());
        title.setBounds((800 - stringWidth) / 2, 40, stringWidth, 60);
        panel.add(title);
        // Bottone Play
        JButton playButton = createStyledButton("Play");
        playButton.setBounds(290, 140, 220, 50);
        playButton.addActionListener(e -> startGame());
        panel.add(playButton);
        // Bottone Settings
        JButton settingsButton = createStyledButton("Settings");
        settingsButton.setBounds(290, 210, 220, 50);
        settingsButton.addActionListener(e -> new Setting());
        panel.add(settingsButton);
        // Bottone esci dal gioco
        JButton exitButton = createStyledButton("Exit the Game");
        exitButton.setBounds(290, 280, 220, 50);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
        // Bottone selezione del personaggio
        JButton selectCharacterButton = createStyledButton("Select Character");
        selectCharacterButton.setFont(retroFont.deriveFont(Font.PLAIN, 14f));
        selectCharacterButton.setBounds(600, 450, 170, 50);
        selectCharacterButton.addActionListener(e -> showCharacterSelector());
        panel.add(selectCharacterButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(retroFont);
        button.setBackground(fgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 255, 180));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(fgColor);
            }
        });

        return button;
    }

    private void startGame() {
        if (selectedCharacter == null) {
            JOptionPane.showMessageDialog(this, "Please select a character first.");
        } else {
            JOptionPane.showMessageDialog(this, "Starting game with: " + selectedCharacter);

            GamePanel gamePanel = new GamePanel(selectedCharacter); 
            JFrame gameFrame = new JFrame();
            gameFrame.setTitle("Game");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setUndecorated(true);
            gameFrame.add(gamePanel);
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gamePanel.setupGame();
            gamePanel.startGameThread();
            this.dispose();
        }
    }

    private void showCharacterSelector() {
        JDialog selectorDialog = new JDialog(this, "Select Your Character", true);
        selectorDialog.setUndecorated(true);
        selectorDialog.setSize(700, 250);
        selectorDialog.setLocationRelativeTo(this);
        selectorDialog.setLayout(new BorderLayout());

        JPanel charPanel = new JPanel();
        charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.X_AXIS));
        charPanel.setBackground(new Color(30, 30, 30));

        String[] characters = { "Cat", "Fox", "FoxFire", "WIP" };
        HashMap<String, JPanel> panelMap = new HashMap<>();

        // Use a temporary variable to track selection in the dialog
        final String[] tempSelected = { selectedCharacter };

        for (String name : characters) {
            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(100, 120));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            p.setBackground(name.equals(selectedCharacter) ? Color.GREEN : Color.WHITE);

            JLabel imgLabel;
            String folderName = name.replaceAll("\\s+", "").toLowerCase();
            String path = "src/main/java/res/player/" + folderName + "/Left0.png";
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();
            if (img == null || icon.getIconWidth() <= 0) {
                imgLabel = new JLabel("?");
                imgLabel.setPreferredSize(new Dimension(64, 64));
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imgLabel.setVerticalAlignment(SwingConstants.CENTER);
            } else {
                Image scaledImg = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(scaledImg));
            }
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(retroFont);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            p.add(Box.createVerticalStrut(10));
            p.add(imgLabel);
            p.add(Box.createVerticalStrut(5));
            p.add(nameLabel);

            p.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    tempSelected[0] = name;
                    for (String key : panelMap.keySet()) {
                        panelMap.get(key).setBackground(Color.WHITE);
                    }
                    p.setBackground(Color.GREEN);
                }
            });

            panelMap.put(name, p);
            charPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            charPanel.add(p);
        }

        JScrollPane scrollPane = new JScrollPane(charPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(700, 150));

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(retroFont);
        confirmButton.setBackground(new Color(180, 255, 180));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.addActionListener(e -> {
            selectedCharacter = tempSelected[0]; // Save the selection
            selectorDialog.dispose();
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(30, 30, 30));
        bottom.add(confirmButton);

        selectorDialog.add(scrollPane, BorderLayout.CENTER);
        selectorDialog.add(bottom, BorderLayout.SOUTH);
        selectorDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
