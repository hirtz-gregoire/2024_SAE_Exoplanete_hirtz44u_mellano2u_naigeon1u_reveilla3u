package image_processors.processors;

import image_processors.Processor;
import tools.cluster.Clustering;
import tools.ColorTool;
import tools.Palette;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Clusterer implements Processor {

    // ----------------------------------------------------------

    public static final int CLUSTER_BY_COLOR = 0;
    public static final int CLUSTER_BY_POSITION = 1;
    public static final int CLUSTER_BY_COLOR_AND_POSITION = 2;

    // -----------------------------------------------------------

    private Clustering clusteringAlgorithm;
    private Palette palette;
    private int clusterMode;
    private StepExporter exporter;

     public Clusterer(Clustering clusteringAlgorithm, int clusterMode, StepExporter exporter, Palette palette) {
         this.clusteringAlgorithm = clusteringAlgorithm;
         this.palette = palette;
         if(clusterMode < 0 || clusterMode > 2) {
             clusterMode = 0;
         }
         this.clusterMode = clusterMode;
         this.exporter = exporter;
     }
    @Override
    public BufferedImage process(BufferedImage image) {
         System.out.println("Clusterer processing...");
        ArrayList<double[]> data = new ArrayList<>();

        // foreach pixel
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double[] pixelData = new double[0];

                switch (clusterMode) {
                    case CLUSTER_BY_COLOR:
                        int color = image.getRGB(x, y);
                        int[] colors = ColorTool.getTabColor(color);
                        pixelData = new double[]{colors[0], colors[1], colors[2]};
                        break;
                    case CLUSTER_BY_POSITION:
                        pixelData = new double[]{x, y};
                        break;
                    case CLUSTER_BY_COLOR_AND_POSITION:
                        color = image.getRGB(x, y);
                        colors = ColorTool.getTabColor(color);
                        pixelData = new double[]{colors[0], colors[1], colors[2], x, y};
                        break;
                }

                data.add(pixelData);
            }
        }

        System.out.println("Clustering values...");
        int[] clusters = clusteringAlgorithm.cluster(data.toArray(new double[0][0]));
        int maxValue = Arrays.stream(clusters).max().getAsInt();
        int i = 0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, getColorGradient(clusters[i++], maxValue).getRGB());
            }
        }

        return image;
    }

    public static Color getColorGradient(double value, double maxValue) {
         double normalizedValue = value/maxValue;

         int[] color0 = ColorTool.getTabColor(Color.BLUE.getRGB());
         int[] color05 = ColorTool.getTabColor(Color.GREEN.getRGB());
         int[] color1 = ColorTool.getTabColor(Color.YELLOW.getRGB());

         if(normalizedValue <= 0.5) {
             int r = (int) (color0[0] * (1-normalizedValue) + color05[0] * normalizedValue);
             int g = (int) (color0[1] * (1-normalizedValue) + color05[1] * normalizedValue);
             int b = (int) (color0[2] * (1-normalizedValue) + color05[2] * normalizedValue);
             return new Color(r, g, b);
         }
         else {
             int r = (int) (color05[0] * (1-normalizedValue) + color1[0] * normalizedValue);
             int g = (int) (color05[1] * (1-normalizedValue) + color1[1] * normalizedValue);
             int b = (int) (color05[2] * (1-normalizedValue) + color1[2] * normalizedValue);
             return new Color(r, g, b);
         }
    }
}
