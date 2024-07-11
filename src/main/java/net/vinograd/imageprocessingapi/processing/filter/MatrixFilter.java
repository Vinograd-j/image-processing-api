package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

import java.awt.*;
import java.util.List;

public abstract class MatrixFilter implements Convertable{

    protected Image image;
    protected List<List<Double>> matrix;

    public MatrixFilter(Image image) {
        this.image = image;
    }

    @Override
    public Image convert() {

        int width = image.getWidth();
        int height = image.getHeight();

        int matrixSize = matrix.size();

        int indent = matrixSize / 2;

        return image.mapToNew((x, y) -> {
            double sumR = 0;
            double sumG = 0;
            double sumB = 0;

            for (int ky = 0; ky < matrixSize; ky++) {
                for (int kx = 0; kx < matrixSize; kx++) {

                    int pixelX = x - indent + kx;
                    int pixelY = y - indent + ky;

                    if (pixelX < 0)
                        pixelX = -pixelX;
                    else if (pixelX >= width)
                        pixelX = width - (pixelX - width) - 1;

                    if (pixelY < 0)
                        pixelY = -pixelY;
                    else if (pixelY >= height)
                        pixelY = height - (pixelY - height) - 1;

                    int rgb = image.getRGB(pixelX, pixelY);
                    Color color = new Color(rgb);

                    double multiplier = matrix.get(ky).get(kx);

                    sumR += color.getRed() * multiplier;
                    sumG += color.getGreen() * multiplier;
                    sumB += color.getBlue() * multiplier;

                }
            }

            int newRed = Math.min(Math.max((int) sumR, 0), 255);
            int newGreen = Math.min(Math.max((int) sumG, 0), 255);
            int newBlue = Math.min(Math.max((int) sumB, 0), 255);

            Color color = new Color(newRed, newGreen, newBlue);

            return new PixelColor(color);
        });
    }

}