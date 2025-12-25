package org.example.main;

import openize.heic.decoder.HeicImage;
import openize.io.IOFileStream;
import openize.io.IOMode;

import java.awt.image.BufferedImage;

import static org.example.util.Util.throwError;

public class HEICHandler {
    // https://products.openize.com/heic/java/
    static BufferedImage readHEIC(String path) {
        try (IOFileStream fs = new IOFileStream(path, IOMode.READ)) {
            HeicImage image = HeicImage.load(fs);

            int[] pixels = image.getInt32Array(openize.heic.decoder.PixelFormat.Argb32);
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image2.setRGB(0, 0, width, height, pixels, 0, width);
            return image2;
        } catch (Exception e) {
            throwError("Error with reading .heic file");
            return null;
        }
    }
}