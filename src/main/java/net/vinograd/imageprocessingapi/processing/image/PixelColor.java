package net.vinograd.imageprocessingapi.processing.image;

import java.awt.*;

public class PixelColor {

    private final int red;
    private final int green;
    private final int blue;

    public PixelColor(int red, int green, int blue) {
        this.red = colorInBoundOrError(red);
        this.green = colorInBoundOrError(green);
        this.blue = colorInBoundOrError(blue);
    }

    public PixelColor(Color color){
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public PixelColor(int rgb){
        // Math.max(0, Math.min(255, gray)), Math.max(0, Math.min(255, gray)), Math.max(0, Math.min(255, gray)))
        this(new Color(rgb));
    }

    public Color getAwtColor() {
        return new Color(red, green, blue);
    }

    public int getGrayscale() {
        return (red + green + blue) / 3;
    }

    public int getRgb() {
        return getAwtColor().getRGB();
    }

    private int colorInBoundOrError(int value) {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException();

        return value;
    }

}