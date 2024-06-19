package image_processors.processors;

import image_processors.ImageProcessor;
import image_processors.Processor;
import tools.cluster.Clustering;
import tools.ColorTool;
import tools.Palette;
import tools.cluster.KMeans;

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

    public Clusterer(Clustering clusteringAlgorithm, int clusterMode) {
        this.clusteringAlgorithm = clusteringAlgorithm;
        this.palette = null;
        if(clusterMode < 0 || clusterMode > 2) {
            clusterMode = 0;
        }
        this.clusterMode = clusterMode;
        this.exporter = null;
    }

    public Clusterer(Clustering clusteringAlgorithm, int clusterMode, StepExporter exporter) {
        this.clusteringAlgorithm = clusteringAlgorithm;
        this.palette = null;
        if(clusterMode < 0 || clusterMode > 2) {
            clusterMode = 0;
        }
        this.clusterMode = clusterMode;
        this.exporter = exporter;
    }

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

        // Generate the points to cluster
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

        // Output additional images when clustering by color
        if(exporter != null) {
            System.out.println("Generating clustered output images");
            drawStepImages(image, clusters);
        }

        // Output image is all clusters
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
        double normalizedValue = value / (maxValue + 0.5);
        float hue = (float) (360 * normalizedValue) / 360;

        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    public void drawStepImages(BufferedImage image, int[] clusters) {
        // Image processor to create background images
        Processor[] processes = {
                new DuplicateImageByPixel(),
                new IntensityFilter(),
        };

        ImageProcessor imageProcessor = new ImageProcessor(processes);

        // When we have a palette, we draw clusters using it
        if(palette != null) {
            switch (clusterMode) {
                case CLUSTER_BY_COLOR:
                    exportImageWithPaletteCLUSTER_COLOR(image, imageProcessor);
                    break;
                case CLUSTER_BY_COLOR_AND_POSITION:
                    exportImageWithPaletteCLUSTER_COLOR_POSITION(image, imageProcessor, clusters);
                    break;
            }
        }
        // If we don't have palette, we draw individual clusters
        else {
            switch (clusterMode) {
                case CLUSTER_BY_COLOR:
                case CLUSTER_BY_COLOR_AND_POSITION:
                    exportClusteredImage(image, imageProcessor, clusters);
                    break;
            }
        }
    }

    private void exportClusteredImage(BufferedImage image, ImageProcessor processor, int[] clusters) {
        int maxValue = Arrays.stream(clusters).max().getAsInt();

        for(int i = 0; i < maxValue; i++) {
            // Create the background
            BufferedImage outputImage = processor.processImage(image);

            // Draw the colors on it
            int k = 0;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if(clusters[k++] == i) {
                        outputImage.setRGB(x, y, getColorGradient(clusters[k], maxValue).getRGB());
                    }
                }
            }

            // Output the image
            exporter.process(outputImage);
        }
    }

    private void exportImageWithPaletteCLUSTER_COLOR(BufferedImage image, ImageProcessor processor) {
        // When a palette is given, we assume it's the palette of the image and just draw the pixels that match the palette
        for(Color color : palette.getColors()) {
            // Create the background
            BufferedImage outputImage = processor.processImage(image);

            // Draw the colors on it
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if(image.getRGB(x, y) == color.getRGB()) {
                        outputImage.setRGB(x, y, color.getRGB());
                    }
                }
            }

            // Output the image
            exporter.process(outputImage);
        }
    }

    private void exportImageWithPaletteCLUSTER_COLOR_POSITION(BufferedImage image, ImageProcessor processor, int[] clusters) {
        // When a palette is given, we assume it's the palette of the image and just draw the pixels that match the palette
        for(Color color : palette.getColors()) {
            // Create the background
            BufferedImage outputImage = processor.processImage(image);

            // Draw the colors on it
            int maxValue = Arrays.stream(clusters).max().getAsInt();
            int i = 0;
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if(image.getRGB(x, y) == color.getRGB()) {
                        outputImage.setRGB(x, y, getColorGradient(clusters[i++], maxValue).getRGB());
                    }
                }
            }

            // Output the image
            exporter.process(outputImage);
        }
    }


}
