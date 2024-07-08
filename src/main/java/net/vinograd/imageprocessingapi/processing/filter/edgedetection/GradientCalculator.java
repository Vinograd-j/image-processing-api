package net.vinograd.imageprocessingapi.processing.filter.edgedetection;

public interface GradientCalculator {

    int gradientX(int x, int y);
    int gradientY(int x, int y);

//    default double calculateIntensity(int x, int y){
//        int dx = gradientX(x, y);
//        int dy = gradientY(x, y);
//
//        return Math.sqrt(dy * dx + dy * dy);
//    }

}
