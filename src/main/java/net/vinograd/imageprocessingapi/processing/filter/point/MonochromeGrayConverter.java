package net.vinograd.imageprocessingapi.processing.filter.point;

import net.vinograd.imageprocessingapi.processing.filter.PointFilter;
import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.*;

public class MonochromeGrayConverter extends PointFilter {

    public MonochromeGrayConverter(Image image) {
        super(image);
    }

    @Override
    protected Color mapColor(Color color) {
        int grayValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

        return new Color(grayValue, grayValue, grayValue);
    }

}
