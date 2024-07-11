package net.vinograd.imageprocessingapi.processing.edgedetection.gradient;

import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

public class SobelGradientCalculator implements GradientCalculator{

    private final Image image;

    public SobelGradientCalculator(Image image){
        this.image = image;
    }

    private final byte[][] matrixX =
            {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
            };

    private final byte[][] matrixY =
            {
                    {1, 2, 1},
                    {0, 0, 0},
                    {-1, -2, -1}
            };


    @Override
    public int gradientX(int x, int y) {
        return calculateIntensity(x, y, matrixX);
    }

    @Override
    public int gradientY(int x, int y) {
        return calculateIntensity(x, y, matrixY);
    }

    private int calculateIntensity(int x, int y, byte[][] matrix){
        final int offset = matrix.length / 2;
        final int width = image.getWidth();
        final int height = image.getHeight();

        if (x - offset < 0 || x + offset >= width || y - offset < 0 || y + offset >= height)
            return 0;

        int intensity = 0;

        for (int j = -offset; j <= offset; j++)
            for (int i = -offset; i <= offset; i++)
                intensity += new PixelColor(image.getRGB(x + i, y + j)).getGrayscale() * matrix[offset + j][offset + i];

        return intensity;
    }

}

