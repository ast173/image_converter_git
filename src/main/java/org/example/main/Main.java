package org.example.main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.example.util.Util.*;

public class Main extends JPanel {
    // components
    private final SetupComponents create = new SetupComponents(this);

    // UI components
    JTextField inputTextField;
    JComboBox<String> outputOptions;
    JFileChooser fileChooser;

    // variables
    String outputType = INPUT_TYPES[0];
    BufferedImage inputImage;
    BufferedImage outputImage;

    Main() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setTransferHandler(new FileDropHandler(this));

        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.add(create.leftColumn, BorderLayout.SOUTH);

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.add(create.rightColumn, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(leftWrapper);
        panel.add(rightWrapper);

        this.add(panel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Image Converter v3.0");
        Image icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/icon.png"))).getImage();
        frame.setIconImage(icon);

        Main main = new Main();
        frame.add(main);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}