package net.vinograd.imageprocessingapi.processing.matrix;

public interface MutableMatrix<T> extends Matrix<T> {

    void set(int x, int y, T value);

}