package net.vinograd.imageprocessingapi.processing.filter.point;

import net.vinograd.imageprocessingapi.processing.filter.PointFilter;
import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

import java.awt.*;

public class NegativeFilter extends PointFilter {

    public NegativeFilter(Image image) {
        super(image);
    }

    @Override
    protected Color mapColor(Color color) {
       return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

}
