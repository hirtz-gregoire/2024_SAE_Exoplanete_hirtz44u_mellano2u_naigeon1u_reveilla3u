package tools.paletteDetection;

import tools.Palette;
import tools.norm.ColorNorm;
import tools.norm.NormLab;

import java.awt.image.BufferedImage;

import static java.lang.Math.exp;
import static java.lang.Math.max;

public class CostBasedKMeans implements PaletteFinder {

    private PaletteCostFunction costFunction;

    /**
     * K-Means algorithm for finding the optimal color palette in a picture ( https://en.wikipedia.org/wiki/K-means_clustering )
     * @param costFunction The cost function used to evaluate K-Means palettes
     */
    public CostBasedKMeans(PaletteCostFunction costFunction) {
        this.costFunction = costFunction;
    }

    @Override
    public Palette findPalette(BufferedImage image) {
        // TODO
        // Algorithme K-means avec itération sur le nombre
        // De couleurs dans la palette jusqu'à ce que
        // scorePaletteTailleN > scorePaletteTailleN-1
        // scorePalette obtenu grâce à costFunction.evaluatePalette
        throw new UnsupportedOperationException("TODO");
    }
}
