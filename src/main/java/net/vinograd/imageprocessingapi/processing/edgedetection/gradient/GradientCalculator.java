package net.vinograd.imageprocessingapi.processing.edgedetection.gradient;

public interface GradientCalculator {

    int gradientX(int x, int y);
    int gradientY(int x, int y);

    default int calculateIntensity(int x, int y){
        int dx = gradientX(x, y);
        int dy = gradientY(x, y);

        return (int) Math.sqrt(dy * dx + dy * dy);
    }

}
