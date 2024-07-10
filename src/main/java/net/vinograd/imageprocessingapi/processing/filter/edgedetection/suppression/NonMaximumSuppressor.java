package net.vinograd.imageprocessingapi.processing.filter.edgedetection.suppression;

import net.vinograd.imageprocessingapi.processing.filter.edgedetection.gradient.GradientCalculator;
import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;
import net.vinograd.imageprocessingapi.processing.image.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NonMaximumSuppressor {

    private final Image image;
    private final GradientCalculator gradientCalculator;

    public NonMaximumSuppressor(Image image, GradientCalculator gradientCalculator) {
        this.image = image;
        this.gradientCalculator = gradientCalculator;
    }

    public Image suppressNonMaximum() {
        return image.mapToNew((x, y) ->{
            int dx = gradientCalculator.gradientX(x, y);
            int dy = gradientCalculator.gradientY(x, y);

            double gradientAngle = calculateVectorAngle(dx, dy);

            List<Point> neighbourPoints = neighboursPoints(x, y, gradientAngle);

            int intensity = intensity(x, y);

            List<Integer> neighbourIntensities = neighbourPoints.stream()
                    .map(point -> intensity(point.getX(), point.getY()))
                    .toList();

            return neighbourIntensities.isEmpty() || Collections.max(neighbourIntensities) > intensity ? new PixelColor(0,0,0) : new PixelColor(intensity, intensity, intensity);
        });
    }

    private double calculateVectorAngle(int x, int y) {
        return Math.round(Math.atan2(x, y) / (Math.PI / 4)) * (Math.PI / 4) - Math.PI / 2;
    }

    private List<Point> neighboursPoints(int x, int y, double gradientAngle) {
        List<Point> neighboursIntensity = new ArrayList<>();

        int dxInDirection = (int) Math.signum(Math.cos(gradientAngle));
        int dyInDirection = (int) Math.signum(Math.sin(gradientAngle));

        addNeighboursIntensity(x + dxInDirection, y + dyInDirection, neighboursIntensity);
        addNeighboursIntensity(x - dxInDirection, y - dyInDirection, neighboursIntensity);

        return neighboursIntensity;
    }

    private void addNeighboursIntensity(int x, int y, List<Point> neighboursIntensity) {
        if (x >= 0 && x <= image.getWidth() && y >= 0 && y <= image.getHeight())
            neighboursIntensity.add(new Point(x, y));
    }

    private int intensity(int x, int y) {
        int dx = gradientCalculator.gradientX(x, y);
        int dy = gradientCalculator.gradientY(x, y);
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

}