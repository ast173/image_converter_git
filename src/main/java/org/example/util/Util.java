package org.example.util;

import org.example.main.Main;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;

public class Util {
    static Main main;

    public static final String[] INPUT_TYPES = {".png", ".jpeg", ".jpg", ".heic", ".webp", ".ico"};
    public static final String[] OUTPUT_TYPES = {".png", ".jpeg", ".jpg", ".webp"};
    public static final String[] ALPHA_TYPES = {".png", ".webp", ".ico"};
    public static final String[] NON_ALPHA_TYPES = {".jpeg", ".jpg"};

    public static final int SCREEN_WIDTH = 550;
    public static final int SCREEN_HEIGHT = 550;

    public static void throwError(String message) {
        JOptionPane.showMessageDialog(main, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void throwMessage(String message) {
        JOptionPane.showMessageDialog(main, message);
    }

    public static boolean isAcceptableFileFormat(String path) {
        File file = new File(path);
        String fileName = file.getName().toLowerCase();
        for (String extension : Util.INPUT_TYPES) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static boolean supportsAlpha(String type) {
        return Arrays.asList(Util.ALPHA_TYPES).contains(type);
    }
}