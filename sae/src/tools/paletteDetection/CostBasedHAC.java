package tools.paletteDetection;

import tools.Palette;
import tools.norm.ColorNorm;
import tools.norm.NormLab;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class CostBasedHAC implements PaletteFinder {

    private PaletteCostFunction costFunction;
    private static ColorNorm colorNorm = new NormLab();

    /**
     * Custom algorithm for reducing the number of colors in the image by merging repeatedly colors
     * @param costFunction The cost function used to evaluate palettes
     */
    public CostBasedHAC(PaletteCostFunction costFunction) {
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
        // Collect all unique colors from the image
        System.out.println("Collect all unique colors from the image");
        Set<Color> uniqueColors = new HashSet<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                uniqueColors.add(new Color(image.getRGB(x, y)));
            }
        }

        // Convert the set of colors to an array
        System.out.println("Convert the set of colors to an array");
        Color[] colors = uniqueColors.toArray(new Color[0]);
        inOutPalette.setColors(colors);

        double oldScore = -1;
        int iteration = 0;
        while (true) {
            System.out.println("Iteration " + iteration++);
            // Find the two closest colors and merge them
            System.out.println("Find the two closest colors and merge them");
            int index1 = -1, index2 = -1;
            double minDistance = Double.MAX_VALUE;
            for (int i = 0; i < colors.length; i++) {
                for (int j = i + 1; j < colors.length; j++) {
                    double distance = colorNorm.colorDistance(colors[i], colors[j]);
                    System.out.println("Find the two closest colors and merge them "+i+" "+j);
                    if (distance < minDistance) {
                        minDistance = distance;
                        index1 = i;
                        index2 = j;
                    }
                }
            }

            // Merge the two colors
            System.out.println("Merge the two colors");
            Color color1 = colors[index1];
            Color color2 = colors[index2];
            Color mergedColor = new Color(
                    (color1.getRed() + color2.getRed()) / 2,
                    (color1.getGreen() + color2.getGreen()) / 2,
                    (color1.getBlue() + color2.getBlue()) / 2
            );

            // Replace the two colors with the merged color in a new array
            System.out.println("Replace the two colors with the merged color in a new array");
            Color[] newColors = new Color[colors.length - 1];
            int index = 0;
            for (int i = 0; i < colors.length; i++) {
                if (i != index1 && i != index2) {
                    newColors[index++] = colors[i];
                }
            }
            newColors[index] = mergedColor;

            if (iteration == 0) {
                // Calculate the initial score
                System.out.println("Calculate the initial score");
                oldScore = costFunction.evaluatePalette(image, inOutPalette);
            }

            // Calculate the new score
            System.out.println("Calculate the new score");
            inOutPalette.setColors(newColors);
            double newScore = costFunction.evaluatePalette(image, inOutPalette);

            // If the new score is not less than the old score, break the loop
            System.out.println("If the new score is not less than the old score, break the loop");
            if (oldScore != -1 && newScore >= oldScore) {
                break;
            }

            // Otherwise, update the colors and the old score
            System.out.println("Otherwise, update the colors and the old score");
            colors = newColors;
            oldScore = newScore;
        }

        // Set the final colors
        System.out.println("Set the final colors");
        inOutPalette.setColors(colors);
    }
}
