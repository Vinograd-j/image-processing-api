package net.vinograd.imageprocessingapi.processing.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Image {

    private final BufferedImage image;

    public Image(byte[] bytes) {
        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validatePixel(int x, int y) {
        if (x <= 0 || x >= image.getWidth())
            throw new IndexOutOfBoundsException("X = " + x + "out of bounds");
        if (y <= 0 || y >= image.getHeight())
            throw new IndexOutOfBoundsException("Y = " + y + "out of bounds");
    }

    public void setRGB(int x, int y, int rgb) {
        validatePixel(x, y);
        image.setRGB(x, y, rgb);
    }

    public int getRGB(int x, int y) {
        return image.getRGB(x, y);
    }

    public int getWidth(){
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }

}
