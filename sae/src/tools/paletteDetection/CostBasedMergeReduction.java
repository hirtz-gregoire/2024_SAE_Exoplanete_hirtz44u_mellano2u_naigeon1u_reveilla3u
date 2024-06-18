package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public class CostBasedMergeReduction implements PaletteFinder {

    private PaletteCostFunction costFunction;
    /**
     * Custom algorithm for reducing the number of colors in the image by merging repeatedly colors
     * @param costFunction The cost function used to evaluate palettes
     */
    public CostBasedMergeReduction(PaletteCostFunction costFunction) {
        this.costFunction = costFunction;
    }

    @Override
    public Palette findPalette(BufferedImage image) {
        // TODO
        // Initialisation : collecter toutes les couleurs de l'image
        // ALGO :
        // - Calcul du score (costFunction.evaluatePalette)
        // - Fusion des deux couleurs les plus proches
        // - Calcul du score 
        // - Si score après fusion < score avant fusion, répété l'algorithme
        throw new UnsupportedOperationException("TODO");
    }
}
