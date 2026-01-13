package org.example.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.example.util.Util.*;

public class Main extends JPanel {
    // screen settings
    private static final int PANEL_WIDTH = 550;
    private static final int PANEL_HEIGHT = 550;
    private final JPanel panel = new JPanel(null) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            if (inputImage != null) {
                drawScaledImage(g2, inputImage, 50, 300);
                drawScaledImage(g2, outputImage, 300, 300);
            }

            drawBoxOutline(g2, 50, 300);
            drawBoxOutline(g2, 300, 300);
        }
    };

    // UI components
    JTextField inputTextField;
    JComboBox<String> outputOptions;
    JFileChooser fileChooser;

    // variables
    String outputType = INPUT_TYPES[0];
    BufferedImage inputImage;
    BufferedImage outputImage;

    Main() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setTransferHandler(new FileDropHandler(this));

        panel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        centerPanel();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerPanel();
            }
        });

        SetupComponents create = new SetupComponents(this);
        create.mainComponents(panel);
        create.convertionComponents(panel);
        create.flipComponents(panel);
        this.add(panel);
    }

    private void centerPanel() {
        int cornerX = (getWidth() - PANEL_WIDTH) / 2;
        int cornerY = (getHeight() - PANEL_HEIGHT) / 2;
        panel.setBounds(cornerX, cornerY, PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void drawScaledImage(Graphics2D g2, BufferedImage image, int x, int y) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scale = Math.min((double) BOX_SIZE / imageWidth, (double) BOX_SIZE / imageHeight);

        int scaledWidth = (int) (imageWidth * scale);
        int scaledHeight = (int) (imageHeight * scale);

        int imageX = x + (BOX_SIZE - scaledWidth) / 2;
        int imageY = y + (BOX_SIZE - scaledHeight) / 2;

        g2.drawImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH), imageX, imageY, this);
    }

    private void drawBoxOutline(Graphics2D g2, int x, int y) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, BOX_SIZE, BOX_SIZE);
    }

    public static void main(String[] args) {
        JFrame screen = new JFrame();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setTitle("Image Converter v2.1");
        Image icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/icon.png"))).getImage();
        screen.setIconImage(icon);

        Main main = new Main();
        screen.add(main);
        screen.pack();

        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
    }
}