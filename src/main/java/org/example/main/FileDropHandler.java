package org.example.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.example.util.Util.*;

// https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-file-path/8456166
public class FileDropHandler extends TransferHandler {
    private Main main;

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
        String inputPath = inputFile.getAbsolutePath();

        // check if file is not an acceptable image type
        if (!isAcceptableFileFormat(inputPath)) {
            throwError("Only " + Arrays.toString(INPUT_TYPES) + " files are supported");
            return false;
        }

        main.inputTextField.setText(inputPath);

        // get image
        try {
            readFile(inputPath, inputFile);
            main.outputImage = main.inputImage;
            main.repaint();
        } catch (Exception e) {
            throwError("Image provided by path doesn't exist");
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private File getFileData(TransferSupport support) {
        try {
            List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            return files.getFirst();
        } catch (UnsupportedFlavorException | IOException e) {
            throwError("UnsupportedFlavorException | IOException");
            return null;
        }
    }

    private void readFile(String inputPath, File inputFile) throws IOException {
        // handle .heic images differently
        if (inputPath.toLowerCase().endsWith(".heic")) {
            main.inputImage = HEICHandler.readHEIC(inputPath);
        } else {
            main.inputImage = ImageIO.read(inputFile);
        }
    }
}