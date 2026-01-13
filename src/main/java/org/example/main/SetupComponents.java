package org.example.main;

import org.example.util.Direction;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import static org.example.util.Util.*;

public class SetupComponents {
    private Main main;
    private Functions func;

    SetupComponents(Main main) {
        this.main = main;
        this.func = new Functions(main);
    }

    void mainComponents(JPanel panel) {
        // input label
        JLabel inputLabel = new JLabel("Input File:");
        inputLabel.setBounds(50, 30, LABEL_WIDTH, LABEL_HEIGHT);
        panel.add(inputLabel);

        // input text field
        main.inputTextField = new JTextField("");
        main.inputTextField.setBounds(50, 50, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        main.inputTextField.addActionListener(e -> func.attemptConvert());
        panel.add(main.inputTextField);

        // clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(100, 150, BUTTON_WIDTH, BUTTON_HEIGHT);
        clearButton.addActionListener(e -> func.clear());
        panel.add(clearButton);

        // about button
        JButton aboutButton = new JButton("About");
        aboutButton.setBounds(100, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        aboutButton.addActionListener(e -> func.getAbout());
        panel.add(aboutButton);

        // exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
    }

    void convertionComponents(JPanel panel) {
        // convert button
        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(100, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        convertButton.addActionListener(e -> func.attemptConvert());
        panel.add(convertButton);

        // output options
        main.outputOptions = new JComboBox<>(OUTPUT_TYPES);
        main.outputOptions.setBounds(225, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        main.outputOptions.setSelectedItem(main.outputType);
        main.outputOptions.addActionListener(e -> func.changeType());
        panel.add(main.outputOptions);

        // file chooser
        // defaults to home folder
        main.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        main.fileChooser.setDialogTitle("Save Image");
    }

    void flipComponents(JPanel panel) {
        // flip horizontally
        JButton horizontalFlipButton = new JButton("Flip ⇄");
        horizontalFlipButton.setBounds(350, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        horizontalFlipButton.addActionListener(e -> func.flip(Direction.HORIZONTALLY));
        panel.add(horizontalFlipButton);

        // flip vertically
        JButton verticalFlipButton = new JButton("Flip ⇅");
        verticalFlipButton.setBounds(350, 150, BUTTON_WIDTH, BUTTON_HEIGHT);
        verticalFlipButton.addActionListener(e -> func.flip(Direction.VERTICALLY));
        panel.add(verticalFlipButton);

        // rotate clockwise
        JButton rotateCWButton = new JButton("Rotate ↻");
        rotateCWButton.setBounds(350, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        rotateCWButton.addActionListener(e -> func.rotate(Direction.CLOCKWISE));
        panel.add(rotateCWButton);

        // rotate counterclockwise
        JButton rotateCCWButton = new JButton("Rotate ↺");
        rotateCCWButton.setBounds(350, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        rotateCCWButton.addActionListener(e -> func.rotate(Direction.COUNTER_CLOCKWISE));
        panel.add(rotateCCWButton);

        // original label
        JLabel originalLabel = new JLabel("Original Image:");
        originalLabel.setBounds(50, 500, LABEL_WIDTH, LABEL_HEIGHT);
        panel.add(originalLabel);

        // new label
        JLabel newLabel = new JLabel("New Image:");
        newLabel.setBounds(300, 500, LABEL_WIDTH, LABEL_HEIGHT);
        panel.add(newLabel);
    }
}