package net.vinograd.imageprocessingapi.processing.filter.point;

import net.vinograd.imageprocessingapi.processing.filter.PointFilter;

import java.awt.*;

public class MonochromeGrayConverter extends PointFilter {

    @Override
    protected Color mapColor(Color color) {
        int grayValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

        return new Color(grayValue, grayValue, grayValue);
    }

}
