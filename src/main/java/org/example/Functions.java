package org.example;

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

public class Functions {
    Main main;

    Functions(Main main) {
        this.main = main;
    }

    void getAbout() {
        JOptionPane.showMessageDialog(
                main,
                "Input file types: " + Arrays.toString(Util.inputTypes) +
                        "\nOutput file types: " + Arrays.toString(Util.outputTypes) +
                        "\n" +
                        "\nImages with transparency will be lost when converted to types that don't support it" +
                        "\nFile types with transparency: " + Arrays.toString(Util.alphaTypes) +
                        "\nFile types without transparency: " + Arrays.toString(Util.nonAlphaTypes),
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    void clear() {
        main.inputTextField.setText("");
        main.originalImage = null;
        main.image = null;
        main.repaint();
    }

    void changeType() {
        main.outputType = (String) main.outputOptions.getSelectedItem();
    }

    void attemptConvert() {
        String path = main.inputTextField.getText();
        if (!isValidImage(path)) return;
        if (main.image == null) {
            Util.throwError("Image is null");
            return;
        }

        openFileChooser(path, main.image);
    }

    private boolean isValidImage(String path) {
        if (path.isEmpty()) {
            Util.throwError("Input field is empty");
            return false;
        }

        if (!Util.isAcceptableFileFormat(path)) {
            Util.throwError("Image is an invalid type");
            return false;
        }

        return true;
    }

    private void openFileChooser(String path, BufferedImage image) {
        // make the output file default to the same directory and with the name as the original just with a different extension
        File defaultFile = new File(path.substring(0, path.lastIndexOf(".")) + main.outputType);
        main.fileChooser.setSelectedFile(defaultFile);

        // restrict output to chosen output type
        main.fileChooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(main.outputType, main.outputType.replace(".", ""));
        main.fileChooser.setFileFilter(filter);

        // prompt user
        int result = main.fileChooser.showSaveDialog(main);
        if (result != JFileChooser.APPROVE_OPTION) return;
        File outputFile = main.fileChooser.getSelectedFile();

        // if a file with the same name already exists, confirm overwrite
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
            Util.throwMessage("Image conversion has been canceled");
            return false;
        }
        return true;
    }

    // https://stackoverflow.com/questions/14618953/image-conversion-in-java
    private void convertImage(BufferedImage image, File outputFile) {
        try {
            String outputType = main.outputType.replace(".", "");

            // remove transparency if converting to a type that doesn't support it
            if (!Util.supportsAlpha(outputType) && image.getColorModel().hasAlpha()) {
                image = removeAlphaLayer(image);
                System.out.println("Transparent pixels has been replaced with white (#ffffff)");
            }

            // convert
            if (ImageIO.write(image, outputType, outputFile)) {
                Util.throwMessage("Converted to " + main.outputType);
            } else {
                Util.throwError("Failed to convert to " + main.outputType);
            }
        } catch (IOException e) {
            Util.throwError("IOException");
        }
    }

    private BufferedImage removeAlphaLayer(BufferedImage image) {
        BufferedImage rgbImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2 = rgbImage.createGraphics();
        g2.drawImage(image, 0, 0, Color.WHITE, null); // paint background white
        g2.dispose();

        return rgbImage;
    }

    // https://stackoverflow.com/questions/9558981/flip-image-with-graphics2d
    void flipHorizontal(BufferedImage image) {
        if (main.image == null) {
            Util.throwError("An image has not been loaded yet");
            return;
        }
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        main.image = op.filter(image, null);
        main.repaint();
    }

    void flipVertical(BufferedImage image) {
        if (main.image == null) {
            Util.throwError("An image has not been loaded yet");
            return;
        }
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        main.image = op.filter(image, null);
        main.repaint();
    }

    // https://stackoverflow.com/questions/8639567/java-rotating-images
    void rotate(BufferedImage image, int degrees) {
        if (main.image == null) {
            Util.throwError("An image has not been loaded yet");
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(height, width, image.getType());
        Graphics2D g2 = newImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        AffineTransform tx = new AffineTransform();

        switch (degrees % 360) {
            case 90:
                tx.translate(height, 0);
                tx.rotate(Math.toRadians(90));
                break;
            case -90:
                tx.translate(0, width);
                tx.rotate(Math.toRadians(-90));
                break;
            default:
                Util.throwError("Degrees must be 90 or -90");
                return;
        }

        g2.drawImage(image, tx, null);
        g2.dispose();

        main.image = newImage;
        main.repaint();
    }
}