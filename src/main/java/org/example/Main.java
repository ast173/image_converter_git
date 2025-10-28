package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

// TODO: add .heic and .ico writing
// TODO: .svg r/w

public class Main extends JPanel {
    // screen settings
    private final int screenWidth = 550;
    private final int screenHeight = 550;

    // path
    private final String homePath = Paths.get("").toAbsolutePath().toString();

    // components
    private final SetupComponents create = new SetupComponents(this);

    // UI components
    // convert tool components
    JLabel inputLabel;
    JTextField inputTextField;
    JButton clearButton;
    JButton exitButton;
    JButton aboutButton;

    // convert tool components
    JButton convertButton;
    JComboBox<String> outputOptions;
    JFileChooser fileChooser;

    // flip tool components
    JButton horizontalFlipButton;
    JButton verticalFlipButton;
    JButton rotateCWButton;
    JButton rotateCCWButton;
    JLabel originalLabel;
    JLabel newLabel;

    // variables
    String outputType = Util.inputTypes[0];
    BufferedImage originalImage;
    BufferedImage image;

    Main() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true); // enables double buffering for smoother rendering
        this.setFocusable(true); // ensure the panel is focusable
        this.setLayout(null); // disable layout manager to use absolute positioning
        this.setTransferHandler(new FileDropHandler(this)); // allows files to be dropped

        // create components
        create.mainComponents();
        create.convertionComponents();
        create.flipComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawBox(g2, 50, 300, 200, 200);
        drawBox(g2, 300, 300, 200, 200);

        if (originalImage != null) {
            drawScaledImage(g2, originalImage, 50, 300, 200, 200);
            drawScaledImage(g2, image, 300, 300, 200, 200);
        }

        drawBoxOutline(g2, 50, 300, 200, 200);
        drawBoxOutline(g2, 300, 300, 200, 200);
    }

    private void drawScaledImage(Graphics2D g2, BufferedImage image, int boxX, int boxY, int boxWidth, int boxHeight) {
        // draw image
        double scale = Math.min((double) boxWidth / image.getWidth(), (double) boxHeight / image.getHeight());

        int scaledWidth = (int) (image.getWidth() * scale);
        int scaledHeight = (int) (image.getHeight() * scale);

        int imageX = boxX + (boxWidth - scaledWidth) / 2;
        int imageY = boxY + (boxHeight - scaledHeight) / 2;

        g2.drawImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH), imageX, imageY, this);
    }

    private void drawBox(Graphics2D g2, int boxX, int boxY, int boxWidth, int boxHeight) {
        g2.setColor(Color.decode("#eeeeee"));
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);
    }

    private void drawBoxOutline(Graphics2D g2, int boxX, int boxY, int boxWidth, int boxHeight) {
        g2.setColor(Color.BLACK);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
    }

    public static void main(String[] args) {
        JFrame screen = new JFrame();
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        screen.setResizable(false); // TODO toggle?
        screen.setTitle("Image Converter");
        screen.setIconImage(new ImageIcon("C:/Users/bowen/Desktop/Code/Java/ImageConverterMaven/icon.png").getImage());

        Main main = new Main();
        screen.add(main);
        screen.pack();

        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
    }
}