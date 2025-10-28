package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;

public class SetupComponents {
    Main main;
    Functions func;

    SetupComponents(Main main) {
        this.main = main;
        this.func = new Functions(main);
    }

    void mainComponents() {
        // input label
        main.inputLabel = new JLabel("Input File:");
        main.inputLabel.setBounds(50, 30, 100, 20);
        main.add(main.inputLabel);

        // input text field
        main.inputTextField = new JTextField("");
        main.inputTextField.setBounds(50, 50, 200, 30);
        main.inputTextField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "convertAction");
        main.inputTextField.getActionMap().put("convertAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                func.attemptConvert();
            }
        });
        main.add(main.inputTextField);

        // clear button
        main.clearButton = new JButton("Clear");
        main.clearButton.setBounds(100, 150, 100, 30);
        main.clearButton.addActionListener(e -> func.clear());
        main.add(main.clearButton);

        // about button
        main.aboutButton = new JButton("About");
        main.aboutButton.setBounds(100, 200, 100, 30);
        main.aboutButton.addActionListener(e -> func.getAbout());
        main.add(main.aboutButton);

        // exit button
        main.exitButton = new JButton("Exit");
        main.exitButton.setBounds(100, 250, 100, 30);
        main.exitButton.addActionListener(e -> System.exit(0));
        main.add(main.exitButton);
    }

    void convertionComponents() {
        // convert button
        main.convertButton = new JButton("Convert");
        main.convertButton.setBounds(100, 100, 100, 30);
        main.convertButton.addActionListener(e -> func.attemptConvert());
        main.add(main.convertButton);

        // output options
        main.outputOptions = new JComboBox<>(Util.outputTypes);
        main.outputOptions.setBounds(225, 100, 100, 30);
        main.outputOptions.setSelectedItem(main.outputType);
        main.outputOptions.addActionListener(e -> func.changeType());
        main.add(main.outputOptions);

        // file chooser
        // defaults to home folder
        main.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        // set a title for the dialog
        main.fileChooser.setDialogTitle("File Chooser");
    }

    void flipComponents() {
        // flip horizontally
        main.horizontalFlipButton = new JButton("Flip ⇄");
        main.horizontalFlipButton.setBounds(350, 100, 100, 30);
        main.horizontalFlipButton.addActionListener(e -> func.flipHorizontal(main.image));
        main.add(main.horizontalFlipButton);

        // flip vertically
        main.verticalFlipButton = new JButton("Flip ⇅");
        main.verticalFlipButton.setBounds(350, 150, 100, 30);
        main.verticalFlipButton.addActionListener(e -> func.flipVertical(main.image));
        main.add(main.verticalFlipButton);

        // rotate clockwise
        main.rotateCWButton = new JButton("Rotate ↻");
        main.rotateCWButton.setBounds(350, 200, 100, 30);
        main.rotateCWButton.addActionListener(e -> func.rotate(main.image, 90));
        main.add(main.rotateCWButton);

        // rotate counterclockwise
        main.rotateCCWButton = new JButton("Rotate ↺");
        main.rotateCCWButton.setBounds(350, 250, 100, 30);
        main.rotateCCWButton.addActionListener(e -> func.rotate(main.image, -90));
        main.add(main.rotateCCWButton);

        // original label
        main.originalLabel = new JLabel("Original Image:");
        main.originalLabel.setBounds(50, 500, 100, 20);
        main.add(main.originalLabel);

        // new label
        main.newLabel = new JLabel("New Image:");
        main.newLabel.setBounds(300, 500, 100, 20);
        main.add(main.newLabel);
    }
}