package image_processors.processors.Blur;

import image_processors.Processor;
import image_processors.processors.DuplicateImageByPixel;
import tools.ColorTool;

import java.awt.image.BufferedImage;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class GaussianBlur implements Processor {
    private final int kernelRadius;
    // Spacial difference influence
    private final double sigma_d;

    /**
     * Apply a blurring effect on the image by using a gaussian function to get the weighted average of surround pixels
     * @param kernelSize The size of the filter matrix
     * @param sigma_d As the spatial parameter sigma_d increases, the larger features get smoothened.
     */
    public GaussianBlur(int kernelSize, double sigma_d) {
        this.sigma_d = sigma_d;

        kernelSize = kernelSize - ((kernelSize + 1) % 2);
        kernelSize = Math.max(kernelSize, 3);
        this.kernelRadius = kernelSize/2;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        DuplicateImageByPixel duplicator = new DuplicateImageByPixel();
        BufferedImage res = duplicator.process(image);

        // foreach pixel
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double weight_sum = 0;
                double[] color_sum = {0, 0, 0};

                // foreach pixel in the kernel
                for(int xi = x - kernelRadius; xi < x + kernelRadius; xi++) {
                    // Check if kernel out of bound because "for" loop's values are constant
                    if(xi < 0 || xi >= image.getWidth()) continue;

                    for(int yi = y - kernelRadius; yi < y + kernelRadius; yi++) {
                        // Check if kernel out of bound because "for" loop's values are constant
                        if(yi < 0 || yi >= image.getHeight()) continue;

                        // Euclidian distance between the points
                        double distance = pow(x - xi, 2) + pow(y - yi, 2);

                        // Usage of the differences to get the color weight
                        double weight = exp(-distance / (2.0 * pow(sigma_d, 2)));

                        // Usage of the weight to get the color contribution of the pixel in the kernel
                        int[] xiyiColor = ColorTool.getTabColor(image.getRGB(xi,yi));
                        weight_sum += weight;
                        color_sum[0] += xiyiColor[0] * weight;
                        color_sum[1] += xiyiColor[1] * weight;
                        color_sum[2] += xiyiColor[2] * weight;
                    }
                }

                // New pixel color based on the weighted color sum of all pixels of the kernel
                int[] newColor = new int[3];
                newColor[0] = (int)(color_sum[0] / weight_sum);
                newColor[1] = (int)(color_sum[1] / weight_sum);
                newColor[2] = (int)(color_sum[2] / weight_sum);

                res.setRGB(x, y, ColorTool.getColorIntFromRGB(newColor[0], newColor[1], newColor[2]));
            }
        }

        return res;
    }
}