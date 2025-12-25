package org.example.main;

import org.example.util.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

import static org.example.util.Util.*;

public class Functions {
    private Main main;

    Functions(Main main) {
        this.main = main;
    }

    void getAbout() {
        String aboutText = "Input file types: " + Arrays.toString(INPUT_TYPES) +
                "\nOutput file types: " + Arrays.toString(OUTPUT_TYPES) +
                "\n" +
                "\nImages with transparency will be lost when converted to types that don't support it" +
                "\nFile types with transparency: " + Arrays.toString(ALPHA_TYPES) +
                "\nFile types without transparency: " + Arrays.toString(NON_ALPHA_TYPES);

        JOptionPane.showMessageDialog(main, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    void clear() {
        main.inputTextField.setText("");
        main.inputImage = null;
        main.outputImage = null;
        main.repaint();
    }

    void changeType() {
        main.outputType = (String) main.outputOptions.getSelectedItem();
    }

    void attemptConvert() {
        String path = main.inputTextField.getText();
        if (!isValidImage(path)) return;

        if (main.outputImage == null) {
            throwError("Image is null");
            return;
        }

        openFileChooser(path, main.outputImage);
    }

    private boolean isValidImage(String path) {
        if (path.isEmpty()) {
            throwError("Input field is empty");
            return false;
        }

        if (!isAcceptableFileFormat(path)) {
            throwError("Image is an invalid type");
            return false;
        }

        return true;
    }

    private void openFileChooser(String path, BufferedImage image) {
        // make the output file default to the same directory and with the name as the original just with a different extension
        File defaultFile = new File(path.substring(0, path.lastIndexOf(".")) + main.outputType);
        main.fileChooser.setSelectedFile(defaultFile);

        // restrict output file to chosen output type
        main.fileChooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(main.outputType, main.outputType.replace(".", ""));
        main.fileChooser.setFileFilter(filter);

        // prompt user to confirm
        int result = main.fileChooser.showSaveDialog(main);
        if (result != JFileChooser.APPROVE_OPTION) return;
        File outputFile = main.fileChooser.getSelectedFile();

        // if a file with the same name already exists, prompt user to confirm overwrite
        if (!confirmOverwriteIfExists(outputFile)) return;

        convertImage(image, outputFile);
    }

    private boolean confirmOverwriteIfExists(File outputFile) {
        if (!Files.exists(outputFile.toPath())) return true;

        int choice = JOptionPane.showConfirmDialog(
                main,
                "File already exists. Do you want to overwrite it?\n" + outputFile.toPath(),
                "Select an Option",
                JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) {
            throwMessage("Image conversion has been canceled");
            return false;
        }
        return true;
    }

    // https://stackoverflow.com/questions/14618953/image-conversion-in-java
    private void convertImage(BufferedImage image, File outputFile) {
        try {
            String outputType = main.outputType.replace(".", "");

            // replace transparency with white pixels when converting to a type that doesn't support it
            if (!supportsAlpha(outputType) && image.getColorModel().hasAlpha()) {
                image = replaceAlphaLayerWith(image, Color.WHITE);
                throwMessage("Transparent pixels has been replaced with white (#ffffff)");
            }

            // convert
            if (ImageIO.write(image, outputType, outputFile)) {
                throwMessage("Converted to " + main.outputType);
            } else {
                throwError("Failed to convert to " + main.outputType);
            }
        } catch (IOException e) {
            throwError("IOException");
        }
    }

    private BufferedImage replaceAlphaLayerWith(BufferedImage image, Color color) {
        BufferedImage rgbImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2 = rgbImage.createGraphics();
        g2.drawImage(image, 0, 0, color, null);
        g2.dispose();

        return rgbImage;
    }

    // https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d
    void flip(Direction direction) {
        if (main.outputImage == null) {
            throwError("An image has not been loaded yet");
            return;
        }

        AffineTransform tx;
        switch (direction) {
            case HORIZONTALLY:
                tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-main.outputImage.getWidth(), 0);
                break;
            case VERTICALLY:
                tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -main.outputImage.getHeight());
                break;
            default:
                throwError("Direction must be \"HORIZONTALLY\" or \"VERTICALLY\"");
                return;
        }

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        main.outputImage = op.filter(main.outputImage, null);
        main.repaint();
    }

    // https://stackoverflow.com/questions/8639567/java-rotating-images
    void rotate(Direction direction) {
        if (main.outputImage == null) {
            throwError("An image has not been loaded yet");
            return;
        }

        int width = main.outputImage.getWidth();
        int height = main.outputImage.getHeight();

        BufferedImage newImage = new BufferedImage(height, width, main.outputImage.getType());
        Graphics2D g2 = newImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform tx = new AffineTransform();

        switch (direction) {
            case CLOCKWISE:
                tx.translate(height, 0);
                tx.rotate(Math.toRadians(90));
                break;
            case COUNTER_CLOCKWISE:
                tx.translate(0, width);
                tx.rotate(Math.toRadians(-90));
                break;
            default:
                throwError("Degrees must be \"CLOCKWISE\" or \"COUNTER_CLOCKWISE\"");
                return;
        }

        g2.drawImage(main.outputImage, tx, null);
        g2.dispose();

        main.outputImage = newImage;
        main.repaint();
    }
}