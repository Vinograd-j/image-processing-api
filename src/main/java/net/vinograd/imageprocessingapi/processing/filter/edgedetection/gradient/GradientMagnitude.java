package net.vinograd.imageprocessingapi.processing.filter.edgedetection.gradient;

import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

import java.awt.*;

public class GradientMagnitude {

    private final Image image;
    private final GradientCalculator gradientCalculator;

    public GradientMagnitude(Image image, GradientCalculator gradientCalculator) {
        this.image = image;
        this.gradientCalculator = gradientCalculator;
    }

    public Image apply(){
        return image.mapToNew((x, y) -> new PixelColor(new Color(gradientCalculator.calculateIntensity(x, y), gradientCalculator.calculateIntensity(x, y), gradientCalculator.calculateIntensity(x, y))));
    }

}
