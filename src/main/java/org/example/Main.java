package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import static org.example.util.Util.INPUT_TYPES;

public class Main extends JPanel {
    // screen settings
    private final int SCREEN_WIDTH = 550;
    private final int SCREEN_HEIGHT = 550;

    private final int BOX_SIZE = 200;

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
    String outputType = INPUT_TYPES[0];
    BufferedImage originalImage;
    BufferedImage image;

    Main() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setTransferHandler(new FileDropHandler(this));

        // create components
        create.mainComponents();
        create.convertionComponents();
        create.flipComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (originalImage != null) {
            drawScaledImage(g2, originalImage, 50, 300);
            drawScaledImage(g2, image, 300, 300);
        }

        drawBoxOutline(g2, 50, 300);
        drawBoxOutline(g2, 300, 300);
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
//        screen.setResizable(false); // TODO toggle?
        screen.setTitle("Image Converter v2.1");
        Image icon = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon.png"));
        screen.setIconImage(icon);

        Main main = new Main();
        screen.add(main);
        screen.pack();

        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
    }
}