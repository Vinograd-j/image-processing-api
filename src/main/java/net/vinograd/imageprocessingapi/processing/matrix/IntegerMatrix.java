package net.vinograd.imageprocessingapi.processing.matrix;

import java.util.stream.IntStream;

public class IntegerMatrix implements MutableMatrix<Integer> {

    private final int[][] matrix;

    private final int height;
    private final int width;

    public IntegerMatrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.matrix = new int[height][width];
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Integer get(int x, int y) {
        validatePoint(x, y);
        return matrix[x][y];
    }

    private void validatePoint(int x, int y) {
        if (x < 0 || x >= width )
            throw new IndexOutOfBoundsException("X = " + x + " out of range [0; " + (width - 1) + "]");

        if (y < 0 || y >= height)
            throw new IndexOutOfBoundsException("X = " + x + " out of range [0; " + (height - 1) + "]");
    }

    @Override
    public void forEach(MatrixAction<Integer> action) {
        IntStream.range(0, height).forEach(y ->
                IntStream.range(0, width).forEach(x ->
                        action.perform(x, y, matrix[y][x])));
    }

    @Override
    public boolean isEmpty() {
        return matrix.length == 0;
    }

    @Override
    public void set(int x, int y, Integer value) {
        validatePoint(x, y);
        matrix[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, height).forEach(y -> {
            IntStream.range(0, width).forEach(x -> {
                sb.append(matrix[x][y]);
                if (x < width - 1)
                    sb.append(", ");
            });
            sb.append("\n");
        });

        return sb.toString();
    }

}
