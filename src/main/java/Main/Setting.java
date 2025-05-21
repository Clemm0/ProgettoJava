package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

public class Setting extends JFrame {
    private static final String SETTINGS_FILE = "Setting.ser";
    private SettingsData settings = new SettingsData();

    public Setting() {
        setTitle("Pause Menu");

        loadSettings();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.25);
        int height = (int) (screenSize.height * 0.4);

        setUndecorated(true);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color bgColor = new Color(30, 30, 30);
        Color fgColor = new Color(0, 255, 0);
        Font retroFont = new Font("Monospaced", Font.BOLD, 18);
        Dimension btnSize = new Dimension(200, 40);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("== PAUSED ==");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(fgColor);
        title.setFont(retroFont);

        JButton resumeBtn = new JButton("Resume");
        JButton quitBtn = new JButton("Quit");
        JButton settingsBtn = new JButton("Settings");

        JButton[] mainButtons = new JButton[] { resumeBtn, settingsBtn, quitBtn };
        for (JButton btn : mainButtons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(btnSize);
            btn.setMaximumSize(btnSize);
            btn.setMinimumSize(btnSize);
            btn.setBackground(bgColor);
            btn.setForeground(fgColor);
            btn.setFont(retroFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(fgColor, 2));
        }

        settingsBtn.addActionListener(e -> showSettingsPage(bgColor, fgColor, retroFont));

        resumeBtn.addActionListener(e -> {
            saveSettings();
            dispose();
        });

        quitBtn.addActionListener(e -> {
            saveSettings();
            dispose();
            System.exit(0);
        });

        getRootPane().registerKeyboardAction(
                e -> {
                    saveSettings();
                    dispose();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));
        panel.add(resumeBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(settingsBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quitBtn);

        add(panel);
        setVisible(true);

        saveSettings();
    }

    private void showSettingsPage(Color bgColor, Color fgColor, Font retroFont) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.25);
        int height = (int) (screenSize.height * 0.35);

        JDialog dialog = new JDialog(this, "Settings", true);
        dialog.setUndecorated(true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleBar = new JLabel(" SETTINGS ");
        titleBar.setForeground(fgColor);
        titleBar.setFont(retroFont);
        titleBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleBar);
        panel.add(Box.createVerticalStrut(20));

        JButton volumeBtn = new JButton("Volume");
        JButton screenBtn = new JButton("Screen");
        JButton backBtn = new JButton("Back");

        JButton[] buttons = new JButton[] { volumeBtn, screenBtn, backBtn };
        Dimension btnSize = new Dimension(200, 40);

        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(btnSize);
            btn.setMaximumSize(btnSize);
            btn.setMinimumSize(btnSize);
            btn.setBackground(bgColor);
            btn.setForeground(fgColor);
            btn.setFont(retroFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(fgColor, 2));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

        volumeBtn.addActionListener(e -> {
            dialog.dispose();
            showVolumePanel(bgColor, fgColor, retroFont);
        });

        screenBtn.addActionListener(e -> {
            dialog.dispose();
            showScreenPanel(bgColor, fgColor, retroFont);
        });

        backBtn.addActionListener(e -> {
            saveSettings();
            dialog.dispose();
        });

        addEscapeToBack(dialog, () -> {
            saveSettings();
            dialog.dispose();
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showVolumePanel(Color bgColor, Color fgColor, Font retroFont) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.25);
        int height = (int) (screenSize.height * 0.4);

        JDialog dialog = new JDialog(this, "Volume", true);
        dialog.setUndecorated(true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(" VOLUME ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(fgColor);
        title.setFont(retroFont);

        JLabel[] labels = {
                new JLabel("General"),
                new JLabel("SFX"),
                new JLabel("Music")
        };

        for (JLabel lbl : labels) {
            lbl.setForeground(fgColor);
            lbl.setFont(retroFont.deriveFont(Font.PLAIN, 14));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        JSlider[] sliders = {
                new JSlider(0, 100, settings.generalVolume),
                new JSlider(0, 100, settings.sfxVolume),
                new JSlider(0, 100, settings.musicVolume)
        };

        for (JSlider slider : sliders) {
            slider.setBackground(bgColor);
            slider.setForeground(fgColor);
            slider.setMajorTickSpacing(50);
            slider.setMinorTickSpacing(10);
            slider.setPaintTicks(true);
            slider.setPaintLabels(false);
            slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        sliders[0].addChangeListener(e -> {
            settings.generalVolume = sliders[0].getValue();
            saveSettings();
        });
        sliders[1].addChangeListener(e -> {
            settings.sfxVolume = sliders[1].getValue();
            saveSettings();
        });
        sliders[2].addChangeListener(e -> {
            settings.musicVolume = sliders[2].getValue();
            saveSettings();
        });

        JButton closeBtn = new JButton("Back");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setBackground(bgColor);
        closeBtn.setForeground(fgColor);
        closeBtn.setFont(retroFont);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(BorderFactory.createLineBorder(fgColor, 2));
        closeBtn.addActionListener(e -> {
            saveSettings();
            dialog.dispose();
            showSettingsPage(bgColor, fgColor, retroFont);
        });

        addEscapeToBack(dialog, () -> {
            saveSettings();
            dialog.dispose();
            showSettingsPage(bgColor, fgColor, retroFont);
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        for (int i = 0; i < labels.length; i++) {
            panel.add(labels[i]);
            panel.add(sliders[i]);
            panel.add(Box.createVerticalStrut(5));
        }
        panel.add(Box.createVerticalStrut(10));
        panel.add(closeBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showScreenPanel(Color bgColor, Color fgColor, Font retroFont) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.25);
        int height = (int) (screenSize.height * 0.4);

        JDialog dialog = new JDialog(this, "Screen", true);
        dialog.setUndecorated(true);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(" SCREEN ");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(fgColor);
        title.setFont(retroFont);

        JCheckBox fullscreenBox = new JCheckBox("Fullscreen");
        fullscreenBox.setForeground(fgColor);
        fullscreenBox.setBackground(bgColor);
        fullscreenBox.setFont(retroFont.deriveFont(Font.PLAIN, 14));
        fullscreenBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        fullscreenBox.setSelected(settings.fullscreen);
        fullscreenBox.addActionListener(e -> {
            settings.fullscreen = fullscreenBox.isSelected();
            saveSettings();
        });

        int[] fpsValues = { 30, 40, 60, 90, 120, 150, 240 };
        String[] fpsLabels = { "30", "40", "60", "90", "120", "150", "Unlimited" };
        final int[] fpsIndex = { settings.fpsIndex };

        JLabel fpsLabel = new JLabel("FPS: ");
        fpsLabel.setForeground(fgColor);
        fpsLabel.setFont(retroFont.deriveFont(Font.PLAIN, 14));

        JLabel fpsValue = new JLabel(fpsLabels[fpsIndex[0]]);
        fpsValue.setForeground(fgColor);
        fpsValue.setFont(retroFont.deriveFont(Font.PLAIN, 14));

        JButton minusBtn = new JButton("<");
        JButton plusBtn = new JButton(">");

        JButton[] fpsBtns = { minusBtn, plusBtn };
        for (JButton btn : fpsBtns) {
            btn.setBackground(bgColor);
            btn.setForeground(fgColor);
            btn.setFont(retroFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(fgColor, 2));
        }

        minusBtn.addActionListener(e -> {
            if (fpsIndex[0] > 0) {
                fpsIndex[0]--;
                fpsValue.setText(fpsLabels[fpsIndex[0]]);
                settings.fpsIndex = fpsIndex[0];
                saveSettings();
            }
        });

        plusBtn.addActionListener(e -> {
            if (fpsIndex[0] < fpsLabels.length - 1) {
                fpsIndex[0]++;
                fpsValue.setText(fpsLabels[fpsIndex[0]]);
                settings.fpsIndex = fpsIndex[0];
                saveSettings();
            }
        });

        JPanel fpsPanel = new JPanel();
        fpsPanel.setBackground(bgColor);
        fpsPanel.add(minusBtn);
        fpsPanel.add(fpsLabel);
        fpsPanel.add(fpsValue);
        fpsPanel.add(plusBtn);
        fpsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backBtn = new JButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setBackground(bgColor);
        backBtn.setForeground(fgColor);
        backBtn.setFont(retroFont);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createLineBorder(fgColor, 2));
        backBtn.addActionListener(e -> {
            saveSettings();
            dialog.dispose();
            showSettingsPage(bgColor, fgColor, retroFont);
        });

        addEscapeToBack(dialog, () -> {
            saveSettings();
            dialog.dispose();
            showSettingsPage(bgColor, fgColor, retroFont);
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(fullscreenBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(fpsPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addEscapeToBack(JDialog dialog, Runnable backAction) {
        dialog.getRootPane().registerKeyboardAction(
                e -> backAction.run(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void saveSettings() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE))) {
            out.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        File file = new File(SETTINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                settings = (SettingsData) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                settings = new SettingsData();
            }
        } else {
            settings = new SettingsData();
        }
    }

    public static class SettingsData implements Serializable {
        public int generalVolume = 50;
        public int sfxVolume = 50;
        public int musicVolume = 50;
        public boolean fullscreen = false;
        public int fpsIndex = 2;
    }
}
