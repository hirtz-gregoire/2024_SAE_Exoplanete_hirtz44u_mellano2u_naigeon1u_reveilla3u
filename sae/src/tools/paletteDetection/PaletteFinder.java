package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public interface PaletteFinder {

    /**
     * Method called to find a color palette on an image
     * @param image the image we want to get the color palette from
     * @return a Palette object
     */
    Palette findPalette(BufferedImage image);

    /**
     * Method called to update the colors of a given palette with colors from an image
     * @param image The image we want to get the color palette from
     * @param inOutPalette The palette we want to change
     */
    void findPalette(BufferedImage image, Palette inOutPalette);
}
