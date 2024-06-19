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
     * @param palette The color palette to use
     */
    public ColorReduction(Palette palette) {
        this.palette = palette;
    }

    /**
     * Quantize the image using a given quantization algorithm
     * @param paletteFinder The quantization algorithm to use
     */
    public ColorReduction(PaletteFinder paletteFinder) {
        this.paletteFinder = paletteFinder;
        this.palette = new Palette(new Color[0]);
    }

    /**
     * Quantize the image using a given quantization algorithm
     * @param paletteFinder The quantization algorithm to use
     * @param inOutPalette The palette that will receive the colors found by the algorithm
     */
    public ColorReduction(PaletteFinder paletteFinder, Palette inOutPalette) {
        this.paletteFinder = paletteFinder;
        this.palette = inOutPalette;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        System.out.println("Color reduction...");
        if(paletteFinder != null) {
            // Update the palette by reference, usefull for faster clustering
            System.out.println("Finding color palette...");
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
