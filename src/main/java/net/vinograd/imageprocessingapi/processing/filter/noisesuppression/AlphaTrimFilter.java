package net.vinograd.imageprocessingapi.processing.filter.noisesuppression;

import net.vinograd.imageprocessingapi.processing.filter.Convertable;
import net.vinograd.imageprocessingapi.processing.image.Image;
import net.vinograd.imageprocessingapi.processing.image.PixelColor;

import java.awt.*;
import java.util.ArrayList;

public class AlphaTrimFilter implements Convertable {

    private final Image image;
    private final int windowSideSize;
    private final int trimValue;
    private final int windowSquare;

    public AlphaTrimFilter(Image image, int windowSideSize, int trimValue, int windowSquare) {
        this.image = image;
        this.windowSideSize = windowSideSize;
        this.trimValue = trimValue;
        this.windowSquare = windowSquare;
    }

    @Override
    public Image convert() {
        return image.mapToNew((x, y) -> new PixelColor(calculateAlphaTrimColor(x, y).getRGB()));
    }

    private Color calculateAlphaTrimColor(int x, int y) {
        ArrayList<Color> window = createPixelsWindowAroundPoint(x, y);
        int needPixelsToSum = window.size() - 2 * trimValue;

        if (needPixelsToSum == 0)
            return Color.BLACK;

        if (needPixelsToSum < 0)
            return new Color(image.getRGB(x, y));

        window.sort((color1, color2) -> {

            int redCompare = Integer.compare(color1.getRed(), color2.getRed());
            if (redCompare != 0)
                return redCompare;

            int greenCompare = Integer.compare(color1.getGreen(), color2.getGreen());
            if (greenCompare != 0)
                return greenCompare;

            return Integer.compare(color1.getBlue(), color2.getBlue());
        });

        int sumRed = 0, sumGreen = 0, sumBlue = 0;

        for (int i = trimValue; i < window.size() - trimValue; i++) {
            Color color = window.get(i);

            sumRed += color.getRed();
            sumGreen += color.getGreen();
            sumBlue += color.getBlue();
        }

        int avgRed = sumRed / needPixelsToSum;
        int avgGreen = sumGreen / needPixelsToSum;
        int avgBlue = sumBlue / needPixelsToSum;

        return new Color(avgRed, avgGreen, avgBlue);
    }

    private ArrayList<Color> createPixelsWindowAroundPoint(int x, int y) {
        ArrayList<Color> windowPixels = new ArrayList<>(windowSquare);
        int halfSideSize = windowSideSize / 2;

        for (int j = -halfSideSize; j <= halfSideSize; j++) {
            for (int i = -halfSideSize; i <= halfSideSize; i++) {
                int windowPixelX = x + i;
                int windowPixelY = y + j;
                if (isInsidePoint(windowPixelX, windowPixelY)) {
                    windowPixels.add(new Color(image.getRGB(windowPixelX, windowPixelY)));
                }
            }
        }
        return windowPixels;
    }

    private boolean isInsidePoint(int windowPixelX, int windowPixelY) {
        return windowPixelX >= 0 && windowPixelX < image.getWidth() &&
                windowPixelY >= 0 && windowPixelY < image.getHeight();
    }

}