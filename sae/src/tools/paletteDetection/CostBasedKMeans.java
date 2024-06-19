package tools.paletteDetection;

import tools.ColorTool;
import tools.Palette;
import tools.cluster.KMeans;
import tools.norm.ColorNorm;
import tools.norm.NormLab;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

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
        Palette res = new Palette(new Color[0]);
        findPalette(image, res);
        return res;
    }

    @Override
    public void findPalette(BufferedImage image, Palette inOutPalette) {
        ArrayList<double[]> dataArrayList = new ArrayList<>();

        // Generate the points to cluster
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                int[] colors = ColorTool.getTabColor(color);
                double[] pixelData = new double[]{colors[0], colors[1], colors[2]};

                dataArrayList.add(pixelData);
            }
        }
        double[][] data = dataArrayList.toArray(new double[0][0]);

        // Initialize the values
        double previousScore = 100000000;
        double score = previousScore - 1;
        Color[] bestPalette = new Color[0];
        int colorCount = 1;

        // Try a bigger palette until the palette does not boost the score anymore
        while(score < previousScore) {

            previousScore = score;
            inOutPalette.setColors(bestPalette);

            KMeans kMeans = new KMeans(colorCount);
            int[] clusters = kMeans.cluster(data);

            bestPalette = new Color[colorCount];
            for(int i = 0; i < bestPalette.length; i++) {
                int[] colorSum = new int[]{0,0,0};

                int k = 0;
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        if(clusters[k++] == i) {
                            int[] pixelColor = ColorTool.getTabColor(image.getRGB(x, y));
                            colorSum[0] += pixelColor[0];
                            colorSum[1] += pixelColor[1];
                            colorSum[2] += pixelColor[2];
                        }
                    }
                }

                colorSum[0] /= k;
                colorSum[1] /= k;
                colorSum[2] /= k;

                bestPalette[i] = new Color(colorSum[0], colorSum[1], colorSum[2]);
                score = costFunction.evaluatePalette(image, new Palette(bestPalette));
            }
        }
    }
}
