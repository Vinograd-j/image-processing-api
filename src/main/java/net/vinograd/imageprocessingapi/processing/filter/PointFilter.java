package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PointFilter implements Convertable {

    protected abstract Color mapColor(Color color);

    @Override
    public Image convert(Image image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                result.setRGB(x, y, image.getRGB(x, y));

        return new Image(result);
    }

}
