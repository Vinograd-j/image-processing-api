package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class MatrixFilter implements Convertable{

    protected List<List<Double>> matrix;

    @Override
    public Image convert(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int matrixSize = matrix.size();

        int indent = matrixSize / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
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

                resultImage.setRGB(x, y, color.getRGB());
            }
        }

        return new Image(resultImage);
    }
}
