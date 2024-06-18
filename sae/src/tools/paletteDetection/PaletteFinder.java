package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public interface PaletteFinder {

    Palette findPalette(BufferedImage image);
}
