package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public interface PaletteCostFunction {

    /**
     * Cost function that evaluate the quality of a palette on a given picture
     * @param image The image to test the palette on
     * @param palette The palette to try
     * @return The score of the palette, 0 means a perfect score
     */
    public double evaluatePalette(BufferedImage image, Palette palette);
}
