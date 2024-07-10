package net.vinograd.imageprocessingapi.processing.filter.edgedetection.tracking;

import net.vinograd.imageprocessingapi.processing.filter.edgedetection.threshold.DoubleThresholdFilter;
import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EdgeTrackerHysteresis {

    private final Image image;
    private final List<Direction> directions;

    public EdgeTrackerHysteresis(Image image) {
        this.image = image;

        directions = new ArrayList<>();
        directions.add(new Direction(-1, -1));
        directions.add(new Direction(-1, 0));
        directions.add(new Direction(-1, 1));
        directions.add(new Direction(0, 1));
        directions.add(new Direction(0, -1));
        directions.add(new Direction(1, -1));
        directions.add(new Direction(1, 0));
        directions.add(new Direction(1, 1));

    }

    public Image track(){

        BufferedImage resultImage = image.emptyCopy();

        image.forEach((x, y) -> {
            int color = image.getRGB(x, y);

            if (color != DoubleThresholdFilter.Bound.UPPER_BOUND.getValue()) {
                resultImage.setRGB(x, y, DoubleThresholdFilter.Bound.LOWER_BOUND.getValue());
                return;
            }

            resultImage.setRGB(x, y, DoubleThresholdFilter.Bound.UPPER_BOUND.getValue());

            for (Direction direction : directions) {
                int offsetX = x;
                int offsetY = y;

                while (true){
                    offsetX += direction.dx;
                    offsetY += direction.dy;

                    if (offsetX < 0 || offsetX > image.getWidth() || offsetY < 0 || offsetY > image.getHeight())
                        break;

                    int colorByDirection = image.getRGB(offsetX, offsetY);

                    if (colorByDirection == DoubleThresholdFilter.Bound.UPPER_BOUND.getValue() || colorByDirection == DoubleThresholdFilter.Bound.LOWER_BOUND.getValue())
                        break;

                    resultImage.setRGB(x, y, colorByDirection);
                }
            }
        });

        return new Image(resultImage);
    }

    private record Direction(int dx, int dy) {}

}
