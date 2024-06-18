package tools.paletteDetection;

import tools.Palette;
import tools.norm.ColorNorm;
import tools.norm.NormLab;

import java.awt.image.BufferedImage;

import static java.lang.Math.exp;
import static java.lang.Math.max;

public class ExpoColorCountCost implements PaletteCostFunction {
    private static ColorNorm colorNorm = new NormLab();

    private double exponentRate, colorLimit;

    public ExpoColorCountCost(double exponentRate, double colorLimit) {
        this.exponentRate = exponentRate;
        this.colorLimit = colorLimit;
    }

    /**
     * Cost function for the number of color in the palette ( https://www.desmos.com/calculator/x9wkgnyzz4 )
     * @param colorCount number of color in the palette
     * @return an exponent double, with a value of 1 when colorCount < colorLimit
     */
    private double getColorCountCost(int colorCount) {
        double alpha = max(colorCount, colorLimit);
        return exp(exponentRate * (alpha - colorLimit));
    }

    public double evaluatePalette(BufferedImage image, Palette palette) {
        // TODO
        //
        // Pour chaque pixel, trouvé la couleur la plus proche dans la palette
        // Puis ajouter à la somme total la distance entre la couleur du pixel et la couleur de la palette
        //
        // Return (sommeDistPixels/nbPixels) * getColorCountCost
        throw new UnsupportedOperationException("TODO");
    }
}
