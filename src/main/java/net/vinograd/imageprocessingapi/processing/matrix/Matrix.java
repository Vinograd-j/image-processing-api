package net.vinograd.imageprocessingapi.processing.matrix;

public interface Matrix<T> {

    int getHeight();
    int getWidth();

    T get(int x, int y);

    void forEach(MatrixAction<T> action);

    boolean isEmpty();

    @FunctionalInterface
    interface MatrixAction<T> {
        void perform(int x, int y, T element);
    }

}
