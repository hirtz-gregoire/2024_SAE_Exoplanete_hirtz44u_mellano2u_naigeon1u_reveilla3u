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
    public ColorReduction(PaletteFinder paletteFinder) {
        this.paletteFinder = paletteFinder;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        Palette currentPalette;

        if(palette == null) {
            currentPalette = paletteFinder.findPalette(image);
        }
        else {
            currentPalette = new Palette(palette.getColors());
        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                color = currentPalette.getClosestColor(color);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }
}
