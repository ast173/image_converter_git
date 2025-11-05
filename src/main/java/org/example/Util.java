package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

class Util {
    static Main main;

    static final String[] INPUT_TYPES = {".png", ".jpeg", ".jpg", ".heic", ".webp", ".ico"};
    static final String[] OUTPUT_TYPES = {".png", ".jpeg", ".jpg", ".webp"};
    static final String[] ALPHA_TYPES = {".png", ".webp", ".ico"};
    static final String[] NON_ALPHA_TYPES = {".jpeg", ".jpg"};

    static final Color BACKGROUND_COLOR = Color.decode("#eeeeee");

    static void throwError(String message) {
        JOptionPane.showMessageDialog(main, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void throwMessage(String message) {
        JOptionPane.showMessageDialog(main, message);
    }

    static boolean isAcceptableFileFormat(String path) {
        File file = new File(path);
        String fileName = file.getName().toLowerCase();
        for (String extension : Util.INPUT_TYPES) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    static boolean supportsAlpha(String type) {
        return Arrays.asList(Util.ALPHA_TYPES).contains(type);
    }
}