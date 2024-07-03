package net.vinograd.imageprocessingapi.processing.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Image {

    private final BufferedImage image;

    public Image(BufferedImage image) {
        this.image = image;
    }

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

    public int getRGB(int x, int y) {
        validatePixel(x, y);
        return image.getRGB(x, y);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }

    public byte[] getBytes(String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, outputStream);
        outputStream.flush();
        byte[] imageBytes = outputStream.toByteArray();
        outputStream.close();

        return imageBytes;
    }

}
