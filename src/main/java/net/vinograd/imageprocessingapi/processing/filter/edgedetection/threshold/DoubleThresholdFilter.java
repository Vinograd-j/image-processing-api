package net.vinograd.imageprocessingapi.processing.filter.edgedetection.threshold;

import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

public class DoubleThresholdFilter {

    private final int UPPER_BOUND = 255;
    private final int MIDDLE_BOUND = 127;
    private final int LOWER_BOUND = 0;

    private final int upperBoundPercentage;
    private final int lowerBoundPercentage;

    public DoubleThresholdFilter(int upperBoundPercentage, int lowerBoundPercentage) {
        this.upperBoundPercentage = upperBoundPercentage;
        this.lowerBoundPercentage = lowerBoundPercentage;
    }

    public Image threshold(Image image) {
        return image.mapToNew((x, y) -> {

            double intensityPercentage = new PixelColor(image.getRGB(x, y)).getGrayscale() / 255.0f;

            int newColor = (intensityPercentage <= lowerBoundPercentage) ? LOWER_BOUND :
                    (intensityPercentage < upperBoundPercentage) ? MIDDLE_BOUND :
                            UPPER_BOUND;

            return new PixelColor(newColor);
        });
    }

}