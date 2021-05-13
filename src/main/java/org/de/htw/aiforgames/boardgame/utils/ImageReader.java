package org.de.htw.aiforgames.boardgame.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageReader {

    private static ImageReader instance = null;

    private ImageReader() { }

    /**
     * Read an image from the classpath with the given name
     *
     * @param name the name of the image
     * @return a buffered image
     * @throws IOException if the file was not found or the input stream was unreadable
     */
    public BufferedImage readFromClasspath(String name) throws IOException {
        InputStream imageStream = getClass().getClassLoader().getResourceAsStream(name);
        if (imageStream == null) {
            throw new FileNotFoundException("Could not find image resource with name " + name);
        }
        return ImageIO.read(imageStream);
    }

    /**
     * @return the image reader
     */
    public static ImageReader getInstance() {
        if (instance == null) {
            instance = new ImageReader();
        }
        return instance;
    }
}
