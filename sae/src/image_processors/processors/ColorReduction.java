package image_processors.processors;

import tools.Palette;
import image_processors.Processor;
import tools.paletteDetection.PaletteFinder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorReduction implements Processor {

    private Palette palette;
    private PaletteFinder paletteFinder;

    /**
     * Quantize the image using a given color palette
     * @param palette
     */
    public ColorReduction(Palette palette) {
        this.palette = palette;
    }

    /**
     * Quantize the image using an automatic algorithm
     * @param paletteFinder
     */
    public ColorReduction(PaletteFinder paletteFinder, Palette inOutPalette) {
        this.paletteFinder = paletteFinder;
        this.palette = inOutPalette;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        if(paletteFinder != null) {
            // Update the palette by reference, usefull for faster clustering
            paletteFinder.findPalette(image, palette);
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                color = palette.getClosestColor(color);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }
}
