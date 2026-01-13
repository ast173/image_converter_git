package org.example.main;

import org.example.util.Direction;
import org.example.util.GBC;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;

import static org.example.util.Util.*;

public class SetupComponents {
    private Main main;
    private Functions func;

    JPanel leftColumn = new JPanel(new GridBagLayout());
    JPanel row = new JPanel(new GridBagLayout());
    JPanel rightColumn = new JPanel(new GridBagLayout());

    SetupComponents(Main main) {
        this.main = main;
        this.func = new Functions(main);

        leftColumn();
        rightColumn();
    }

    void leftColumn() {
        GBC gbc = new GBC();
        gbc.fill = GridBagConstraints.BOTH;

        // input label
        JLabel inputLabel = new JLabel("Input File:");
        leftColumn.add(inputLabel);

        // input text field
        gbc.moveDown();
        main.inputTextField = new JTextField("");
        main.inputTextField.addActionListener(e -> func.attemptConvert());
        leftColumn.add(main.inputTextField, gbc);

        // convertion row
        gbc.moveDown();
        singleRow();
        leftColumn.add(row, gbc);

        // clear button
        gbc.moveDown();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> func.clear());
        leftColumn.add(clearButton, gbc);

        // about button
        gbc.moveDown();
        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(e -> func.getAbout());
        leftColumn.add(aboutButton, gbc);

        // exit button
        gbc.moveDown();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        leftColumn.add(exitButton, gbc);

        // original label
        gbc.moveDown();
        JLabel originalLabel = new JLabel("Original Image:");
        leftColumn.add(originalLabel, gbc);
    }

    void singleRow() {
        GBC gbc = new GBC();

        // convert button
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(e -> func.attemptConvert());
        row.add(convertButton, gbc);

        // output options
        gbc.moveRight();
        main.outputOptions = new JComboBox<>(OUTPUT_TYPES);
        main.outputOptions.setSelectedItem(main.outputType);
        main.outputOptions.addActionListener(e -> func.changeType());
        row.add(main.outputOptions, gbc);

        // file chooser
        // defaults to home folder
        main.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        main.fileChooser.setDialogTitle("Save Image");
    }

    void rightColumn() {
        GBC gbc = new GBC();
        gbc.fill = GridBagConstraints.BOTH;

        // flip horizontally
        JButton horizontalFlipButton = new JButton("Flip ⇄");
        horizontalFlipButton.addActionListener(e -> func.flip(Direction.HORIZONTALLY));
        rightColumn.add(horizontalFlipButton, gbc);

        // flip vertically
        gbc.moveDown();
        JButton verticalFlipButton = new JButton("Flip ⇅");
        verticalFlipButton.addActionListener(e -> func.flip(Direction.VERTICALLY));
        rightColumn.add(verticalFlipButton, gbc);

        // rotate clockwise
        gbc.moveDown();
        JButton rotateCWButton = new JButton("Rotate ↻");
        rotateCWButton.addActionListener(e -> func.rotate(Direction.CLOCKWISE));
        rightColumn.add(rotateCWButton, gbc);

        // rotate counterclockwise
        gbc.moveDown();
        JButton rotateCCWButton = new JButton("Rotate ↺");
        rotateCCWButton.addActionListener(e -> func.rotate(Direction.COUNTER_CLOCKWISE));
        rightColumn.add(rotateCCWButton, gbc);

        // new label
        gbc.moveDown();
        JLabel newLabel = new JLabel("New Image:");
        rightColumn.add(newLabel, gbc);
    }
}