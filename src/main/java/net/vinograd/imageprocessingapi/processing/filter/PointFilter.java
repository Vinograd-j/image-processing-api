package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PointFilter implements Convertable {

    protected Image image;

    public PointFilter(Image image) {
        this.image = image;
    }

    protected abstract Color mapColor(Color color);

    @Override
    public Image convert() {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                result.setRGB(x, y, mapColor(new Color(image.getRGB(x, y))).getRGB());

        return new Image(result);
    }

}
