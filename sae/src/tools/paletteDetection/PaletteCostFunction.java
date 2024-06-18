package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public interface PaletteCostFunction {
    public double evaluatePalette(BufferedImage image, Palette palette);
}
