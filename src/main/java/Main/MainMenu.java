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
import javax.swing.SwingUtilities;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JSlider volumeSlider;
    private String selectedCharacter = null;

    public MainMenu() {
        setTitle("Game Main Menu");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createMainMenuPanel(), "main");
        cardPanel.add(createSettingsPanel(), "settings");

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(20, 20, 20));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel title = new JLabel("Game Title", JLabel.CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 48));
        title.setForeground(new Color(255, 220, 235));
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int stringWidth = metrics.stringWidth(title.getText());
        title.setBounds((800 - stringWidth) / 2, 40, stringWidth, 60);
        panel.add(title);

        JButton playButton = createStyledButton("Play");
        playButton.setBounds(290, 140, 220, 50);
        playButton.addActionListener(e -> startGame());
        panel.add(playButton);

        JButton settingsButton = createStyledButton("Settings");
        settingsButton.setBounds(290, 210, 220, 50);
        settingsButton.addActionListener(e -> cardLayout.show(cardPanel, "settings"));
        panel.add(settingsButton);

        JButton exitButton = createStyledButton("Exit the Game");
        exitButton.setBounds(290, 280, 220, 50);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        JButton selectCharacterButton = createStyledButton("Select Character");
        selectCharacterButton.setFont(new Font("Arial", Font.PLAIN, 16));
        selectCharacterButton.setBounds(600, 450, 170, 35);
        selectCharacterButton.addActionListener(e -> showCharacterSelector());
        panel.add(selectCharacterButton);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Courier New", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        volumeLabel.setForeground(Color.LIGHT_GRAY);
        volumeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setBackground(new Color(40, 40, 40));
        volumeSlider.setForeground(new Color(255, 180, 220));
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.addChangeListener(e -> adjustVolume());

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "main"));

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(volumeLabel);
        panel.add(volumeSlider);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Courier New", Font.BOLD, 20));
        button.setBackground(new Color(255, 180, 220));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 210, 235));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 180, 220));
            }
        });

        return button;
    }

    private void startGame() {
        if (selectedCharacter == null) {
            JOptionPane.showMessageDialog(this, "Please select a character first.");
        } else {
            JOptionPane.showMessageDialog(this, "Starting game with: " + selectedCharacter);
            // Qui avvii il gioco (passa al GamePanel o simile)
            GamePanel gamePanel = new GamePanel(); // Assicurati che il gamePanel esista.
            JFrame gameFrame = new JFrame();
            gameFrame.setTitle("Game");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.add(gamePanel);
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gamePanel.setupGame();
            gamePanel.startGameThread();
            this.dispose();  // Chiudi il menu
        }
    }

    private void adjustVolume() {
        int volume = volumeSlider.getValue();
        System.out.println("Volume: " + volume);
    }

    private void showCharacterSelector() {
        JDialog selectorDialog = new JDialog(this, "Select Your Character", true);
        selectorDialog.setSize(700, 250);
        selectorDialog.setLocationRelativeTo(this);
        selectorDialog.setLayout(new BorderLayout());

        JPanel charPanel = new JPanel();
        charPanel.setLayout(new BoxLayout(charPanel, BoxLayout.X_AXIS));
        charPanel.setBackground(new Color(30, 30, 30));

        String[] characters = {"Bubu", "Kiki", "Momo", "Zaza", "Toto"};
        HashMap<String, JPanel> panelMap = new HashMap<>();

        for (String name : characters) {
            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(120, 150));
            p.setLayout(new BorderLayout());
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            p.setBackground(name.equals(selectedCharacter) ? Color.GREEN : Color.WHITE);

            ImageIcon icon = new ImageIcon(name.toLowerCase() + ".png");
            Image scaledImg = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaledImg), JLabel.CENTER);
            JLabel nameLabel = new JLabel(name, JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

            p.add(imgLabel, BorderLayout.CENTER);
            p.add(nameLabel, BorderLayout.SOUTH);

            p.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selectedCharacter = name;
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
        confirmButton.setFont(new Font("Courier New", Font.BOLD, 16));
        confirmButton.setBackground(new Color(180, 255, 180));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        confirmButton.addActionListener(e -> selectorDialog.dispose());

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