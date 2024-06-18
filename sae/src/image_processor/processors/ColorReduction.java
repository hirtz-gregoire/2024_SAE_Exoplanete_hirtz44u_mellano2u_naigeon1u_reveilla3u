package image_processor.processors;

import tools.Palette;
import image_processor.Processor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorReduction implements Processor {

    private Palette palette;
    public ColorReduction(Palette palette) {
        this.palette = palette;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
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
