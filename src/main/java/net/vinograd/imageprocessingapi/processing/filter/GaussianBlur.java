package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.matrix.DoubleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GaussianBlur implements Convertable{

    private final double sigma;

    private final int matrixSize;

    private final DoubleMatrix matrix;

    public GaussianBlur(double sigma, int matrixSize) {
        this.sigma = sigma;
        this.matrixSize = matrixSize;
        this.matrix = new DoubleMatrix(matrixSize, matrixSize);
        createGaussianKernel();
    }

    private void createGaussianKernel(){
        int indent = matrixSize / 2;
        double sum = 0;

        for (int y = -indent; y <= indent; y++) {
            for (int x = -indent; x <= indent; x++) {
                double value = gaussian(x, y);
                sum += value;
                matrix.set(x + indent, y + indent, value);
            }
        }

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix.set(i, j, matrix.get(i, j) / sum);
            }
        }

    }

    @Override
    public BufferedImage convert(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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

                        sumR += color.getRed() * matrix.get(ky, kx);
                        sumG += color.getGreen() * matrix.get(ky, kx);
                        sumB += color.getBlue() * matrix.get(ky, kx);

                    }
                }

                int newRed = Math.min(Math.max((int) sumR, 0), 255);
                int newGreen = Math.min(Math.max((int) sumG, 0), 255);
                int newBlue = Math.min(Math.max((int) sumB, 0), 255);

                Color color = new Color(newRed, newGreen, newBlue);

                resultImage.setRGB(x, y, color.getRGB());
            }
        }

        return resultImage;
    }

    private double gaussian(int x, int y) {
        return (1 / (2 * Math.PI * Math.pow(sigma, 2))) * Math.exp(- ( (x * x + y * y) / (2 * Math.pow(sigma, 2))));
    }

}