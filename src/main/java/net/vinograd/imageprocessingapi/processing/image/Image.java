package net.vinograd.imageprocessingapi.processing.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Image {

    private final BufferedImage image;

    public Image(BufferedImage image) {
        this.image = image;
    }

    public Image(byte[] bytes) throws IOException {
        image = ImageIO.read(new ByteArrayInputStream(bytes));
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

    public int getType(){
        return image.getType();
    }

    public byte[] getBytes(String format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, outputStream);
        outputStream.flush();
        byte[] imageBytes = outputStream.toByteArray();
        outputStream.close();

        return imageBytes;
    }

    public Image mapToNew(BiFunction<Integer, Integer, PixelColor> mapper) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                newImage.setRGB(x, y, mapper.apply(x, y).getRgb());
            }
        }

        return new Image(newImage);
    }

    public void forEach(BiConsumer<Integer, Integer> action) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                action.accept(x, y);
            }
        }
    }

    public BufferedImage emptyCopy(){
        return new BufferedImage(image.getWidth(), getHeight(), image.getType());
    }

}
