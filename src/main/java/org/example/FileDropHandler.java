package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-file-path/8456166
public class FileDropHandler extends TransferHandler {
    Main main;

    FileDropHandler(Main main) {
        this.main = main;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!this.canImport(support)) return false;

        File inputFile = getFileData(support);
        if (inputFile == null) return false;

        // get path
        String inputPath = inputFile.getAbsolutePath();

        // check if file is not an acceptable image type
        if (!Util.isAcceptableFileFormat(inputPath)) {
            Util.throwError("Only " + Arrays.toString(Util.inputTypes) + " files are supported");
            return false;
        }

        main.inputTextField.setText(inputPath);

        // get image
        try {
            // handle .heic images differently
            if (inputPath.toLowerCase().endsWith(".heic")) {
                main.originalImage = HEICHandler.readHEIC(inputPath);
            } else {
                System.out.println(inputFile);
                main.originalImage = ImageIO.read(inputFile);
            }

            main.image = main.originalImage;
            main.repaint();
        } catch (Exception e) {
            Util.throwError("Image provided by path doesn't exist");
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private File getFileData(TransferSupport support) {
        try {
            List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            return files.getFirst();
        } catch (UnsupportedFlavorException | IOException e) {
            Util.throwError("UnsupportedFlavorException | IOException");
            return null;
        }
    }
}