package tools.norm;

import java.awt.*;

public class NormRedMean implements ColorNorm {

    /**
     * ColorNorm implementation that use a "human-like" perception of colors
     * @param c1 The first color to compare
     * @param c2 The second color to compare
     * @return The distance between two colors in the color space
     */
    @Override
    public double colorDistance(Color c1, Color c2) {
        double redShift = 1.0 /2.0 * (c1.getRed() + c2.getRed());

        double deltaR = c1.getRed() - c2.getRed();
        double deltaG = c1.getGreen() - c2.getGreen();
        double deltaB = c1.getBlue() - c2.getBlue();

        double calcR = (2+(redShift/256.0)) * Math.pow(deltaR, 2);
        double calcG = 4 * Math.pow(deltaG, 2);
        double calcB = (2+((255.0-redShift)/256.0)) * Math.pow(deltaB, 2);

        return Math.sqrt(calcR + calcG + calcB);
    }
}
