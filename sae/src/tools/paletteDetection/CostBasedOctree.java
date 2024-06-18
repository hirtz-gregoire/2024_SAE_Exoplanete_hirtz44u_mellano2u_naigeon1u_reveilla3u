package tools.paletteDetection;

import tools.Palette;

import java.awt.image.BufferedImage;

public class CostBasedOctree implements PaletteFinder{

    private PaletteCostFunction costFunction;
    /**
     * Octree algorithm for finding the optimal color palette in a picture ( https://youtu.be/LQST9MITKrw?si=6Yax5eiAJJdjQq0O )
     * @param costFunction The cost function used to evaluate Octree palettes
     */
    public CostBasedOctree(PaletteCostFunction costFunction) {
        this.costFunction = costFunction;
    }

    @Override
    public Palette findPalette(BufferedImage image) {
        // TODO
        // Algorithme Octree PUIS
        // Réduction du nombre de couleurs dans l'arbre jusqu'à
        // scorePaletteTailleN > scorePaletteTailleN+1
        // scorePalette obtenu grâce à costFunction.evaluatePalette
        throw new UnsupportedOperationException("TODO");
    }
}
