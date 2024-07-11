package net.vinograd.imageprocessingapi.processing.filter.edgedetection;

import net.vinograd.imageprocessingapi.processing.filter.edgedetection.gradient.GradientCalculator;
import net.vinograd.imageprocessingapi.processing.filter.edgedetection.gradient.GradientMagnitude;
import net.vinograd.imageprocessingapi.processing.filter.edgedetection.gradient.SobelGradientCalculator;
import net.vinograd.imageprocessingapi.processing.filter.edgedetection.suppression.NonMaximumSuppressor;
import net.vinograd.imageprocessingapi.processing.filter.edgedetection.threshold.DoubleThresholdFilter;
import net.vinograd.imageprocessingapi.processing.filter.edgedetection.tracking.EdgeTrackerHysteresis;
import net.vinograd.imageprocessingapi.processing.filter.matrix.GaussianBlur;
import net.vinograd.imageprocessingapi.processing.filter.point.MonochromeGrayConverter;
import net.vinograd.imageprocessingapi.processing.image.Image;

public class CannyOperator {

    public Image detectEdges(Image image) {
        final double gaussianBlurSigma = 5.0;
        final int gaussianKernelSize = 3;
        final double lowerThresholdPercentage = 0.5;
        final double upperThresholdPercentage = 0.6;

        Image resultImage;

        resultImage = new MonochromeGrayConverter().convert(image);
        resultImage = new GaussianBlur(gaussianBlurSigma, gaussianKernelSize).convert(resultImage);
        GradientCalculator gradientCalculator = new SobelGradientCalculator(resultImage);
        resultImage = new GradientMagnitude(resultImage, gradientCalculator).apply();
        resultImage = new NonMaximumSuppressor(resultImage, gradientCalculator).suppressNonMaximum();
        resultImage = new DoubleThresholdFilter(upperThresholdPercentage, lowerThresholdPercentage).threshold(resultImage);
        resultImage = new EdgeTrackerHysteresis(resultImage).track();

        return resultImage;
    }

}
