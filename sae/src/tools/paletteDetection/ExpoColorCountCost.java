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

    /**
     * Cost function that evaluate the quality of a palette on a given picture using the distance of colors to the palette and applying a penalty for each colors
     * @param exponentRate The rate at which the exponential penalty for additional colors grow
     * @param colorLimit The colorCount score penalty is 0 while colorCount < colorLimit, the penalty for additional color is exponential
     */
    public ExpoColorCountCost(double exponentRate, double colorLimit) {
        this.exponentRate = exponentRate;
        this.colorLimit = colorLimit;
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

    /**
     * Cost function for the number of color in the palette ( https://www.desmos.com/calculator/x9wkgnyzz4 )
     * @param colorCount Number of color in the palette
     * @return An exponent double, with a value of 1 when colorCount < colorLimit
     */
    private double getColorCountCost(int colorCount) {
        double alpha = max(colorCount, colorLimit);
        return exp(exponentRate * (alpha - colorLimit));
    }
}