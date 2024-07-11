package net.vinograd.imageprocessingapi.processing.filter.matrix;

import lombok.Getter;
import lombok.NonNull;
import net.vinograd.imageprocessingapi.processing.filter.MatrixFilter;
import net.vinograd.imageprocessingapi.processing.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class Emboss extends MatrixFilter {

    private final Type type;

    public Emboss(Image image, @NonNull Type embossType) {
        super(image);
        this.matrix = new ArrayList<>();
        this.type = embossType;
        createEmbossMatrix();
    }

    private void createEmbossMatrix() {
        if (type == Type.VERTICAL)
            createVerticalMatrix();
        else if (type == Type.HORIZONTAL)
            createHorizontalMatrix();
    }

    private void createVerticalMatrix(){
        this.matrix.add(Arrays.asList(-1.0, 0.0, 1.0));
        this.matrix.add(Arrays.asList(-1.0, 1.0, 1.0));
        this.matrix.add(Arrays.asList(-1.0, 0.0, 1.0));
    }

    private void createHorizontalMatrix(){
        this.matrix.add(Arrays.asList(-1.0, -1.0, -1.0));
        this.matrix.add(Arrays.asList(0.0, 1.0, 0.0));
        this.matrix.add(Arrays.asList(1.0, 1.0, 1.0));
    }

    public enum Type{
        VERTICAL, HORIZONTAL
    }

}