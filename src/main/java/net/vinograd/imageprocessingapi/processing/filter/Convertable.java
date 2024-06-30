package net.vinograd.imageprocessingapi.processing.filter;

import net.vinograd.imageprocessingapi.processing.image.Image;

import java.awt.image.BufferedImage;

public interface Convertable {

    BufferedImage convert(Image image);

}
