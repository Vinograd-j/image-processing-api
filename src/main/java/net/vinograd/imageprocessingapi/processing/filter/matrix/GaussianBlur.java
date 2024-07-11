package net.vinograd.imageprocessingapi.processing.filter.matrix;

import lombok.Getter;
import net.vinograd.imageprocessingapi.processing.filter.MatrixFilter;
import net.vinograd.imageprocessingapi.processing.image.Image;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GaussianBlur extends MatrixFilter {

    private final double sigma;

    private final int matrixSize;

    public GaussianBlur(Image image, double sigma, int matrixSize) {
        super(image);
        this.sigma = sigma;
        this.matrixSize = matrixSize;

        createGaussianKernel();
    }

    private void createGaussianKernel(){

        matrix = new ArrayList<>(matrixSize);

        for (int i = 0; i < matrixSize; i++) {
            List<Double> row = new ArrayList<>(matrixSize);
            for (int j = 0; j < matrixSize; j++) {
                row.add(0.0);
            }
            matrix.add(row);
        }

        int indent = matrixSize / 2;
        double sum = 0;

        for (int y = -indent; y <= indent; y++) {
            for (int x = -indent; x <= indent; x++) {
                double value = gaussian(x, y);
                sum += value;
                matrix.get(y + indent).set(x + indent, value);
            }
        }

        for (List<Double> row : matrix) {
            for (int x = 0; x < matrixSize; x++) {
                row.set(x, row.get(x) / sum);
            }
        }
    }

    private double gaussian(int x, int y) {
        return (1 / (2 * Math.PI * Math.pow(sigma, 2))) * Math.exp(- ( (x * x + y * y) / (2 * Math.pow(sigma, 2))));
    }

}