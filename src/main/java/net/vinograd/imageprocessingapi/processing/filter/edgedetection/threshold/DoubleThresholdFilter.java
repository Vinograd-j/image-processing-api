package net.vinograd.imageprocessingapi.processing.filter.edgedetection.threshold;

import lombok.Getter;
import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

public class DoubleThresholdFilter {

    private final int upperBoundPercentage;
    private final int lowerBoundPercentage;

    public DoubleThresholdFilter(int upperBoundPercentage, int lowerBoundPercentage) {
        this.upperBoundPercentage = upperBoundPercentage;
        this.lowerBoundPercentage = lowerBoundPercentage;
    }

    public Image threshold(Image image) {
        return image.mapToNew((x, y) -> {

            double intensityPercentage = new PixelColor(image.getRGB(x, y)).getGrayscale() / 255.0f;

            int newColor = (intensityPercentage <= lowerBoundPercentage) ? Bound.LOWER_BOUND.getValue() :
                    (intensityPercentage < upperBoundPercentage) ? Bound.MIDDLE_BOUND.getValue() :
                            Bound.UPPER_BOUND.getValue();

            return new PixelColor(newColor);
        });
    }

    @Getter
    public enum Bound {
        UPPER_BOUND(255),
        MIDDLE_BOUND(127),
        LOWER_BOUND(0);

        private final int value;

        Bound(int i) {
            this.value = i;
        }

    }

}