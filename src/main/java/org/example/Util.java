package org.example;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Base64;

public class Util {
    static Main main;

//    static final String[] inputTypes = {".png", ".jpeg", ".jpg", ".heic", ".webp", ".ico", ".svg"};
//    static final String[] outputTypes = {".png", ".jpeg", ".jpg", ".webp", ".svg"};
    static final String[] inputTypes = {".png", ".jpeg", ".jpg", ".heic", ".webp", ".ico"};
    static final String[] outputTypes = {".png", ".jpeg", ".jpg", ".webp"};
    static final String[] alphaTypes = {".png", ".webp", ".ico"};
    static final String[] nonAlphaTypes = {".jpeg", ".jpg"};

    static void throwError(String message) {
        JOptionPane.showMessageDialog(main, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void throwMessage(String message) {
        JOptionPane.showMessageDialog(main, message);
    }

    static boolean isAcceptableFileFormat(String path) {
        File file = new File(path);
        return isAcceptableFileFormat(file);
    }

    static boolean isAcceptableFileFormat(File file) {
        String fileName = file.getName().toLowerCase();
        for (String extension : Util.inputTypes) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    static boolean supportsAlpha(String type) {
        return Arrays.asList(Util.alphaTypes).contains(type);
    }

    static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}