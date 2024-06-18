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
        //
        // ALGO :
        // - Divisé l'espace des couleurs en 8
        // - Si un des espaces à + de une couleur,
        // - On divise l'espace en 8 (récursivité...)
        //
        // -> Création d'un arbre contenant les couleurs au niveau des branches
        //
        // Réduction du nombre de couleur dans l'arbre
        //    -> On trouve la branche contenant le moins de feuilles (de couleurs)
        //    -> On fusionne toutes les feuilles (couleurs) en fesant la moyenne, créant une nouvelle feuille
        //    -> On évalue le résultat, si il n'est pas satisfaisant (voir condition d'arrêt plus haut), on continue à fusionner des branches
        throw new UnsupportedOperationException("TODO");
    }
}
