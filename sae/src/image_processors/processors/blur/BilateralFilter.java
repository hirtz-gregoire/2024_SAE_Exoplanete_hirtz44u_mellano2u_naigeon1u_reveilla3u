package image_processors.processors.blur;

import image_processors.Processor;
import image_processors.processors.DuplicateImageByPixel;
import tools.ColorTool;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

public class BilateralFilter implements Processor {

    private final int kernelRadius;
    // Spacial difference influence
    private final double sigma_d;
    // Color intensity difference influence
    private final double sigma_r;

    /**
     * Processor used to apply a denoising filter to the image ( https://en.wikipedia.org/wiki/Bilateral_filter )
     * @param kernelSize The size of the filter matrix
     * @param sigma_d As the spatial parameter sigma_d increases, the larger features get smoothened.
     * @param sigma_r As the range parameter sigma_r increases, the filter approach gaussianBlur and the intensity is ignored.
     */
    public BilateralFilter(int kernelSize, double sigma_d, double sigma_r) {
        this.sigma_d = sigma_d;
        this.sigma_r = sigma_r;

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

                int[] xyColor = ColorTool.getTabColor(image.getRGB(x,y));

                // foreach pixel in the kernel
                for(int xi = x - kernelRadius; xi < x + kernelRadius; xi++) {
                    // Check if kernel out of bound because "for" loop's values are constant
                    if(xi < 0 || xi >= image.getWidth()) continue;

                    for(int yi = y - kernelRadius; yi < y + kernelRadius; yi++) {
                        // Check if kernel out of bound because "for" loop's values are constant
                        if(yi < 0 || yi >= image.getHeight()) continue;

                        // Euclidian distance between the points
                        double distance = pow(x - xi, 2) + pow(y - yi, 2);

                        // Color difference between the points
                        int[] xiyiColor = ColorTool.getTabColor(image.getRGB(xi,yi));
                        double intensity_difference = pow(xyColor[0] - xiyiColor[0], 2) + pow(xyColor[1] - xiyiColor[1], 2) + pow(xyColor[2] - xiyiColor[2], 2);

                        // Usage of the differences to get the color weight
                        double spacial_weight = exp(-distance / (2.0 * pow(sigma_d, 2)));
                        double intensity_weight = exp(-intensity_difference / (2.0 * pow(sigma_r, 2)));
                        double weight = spacial_weight * intensity_weight;

                        // Usage of the weight to get the color contribution of the pixel in the kernel
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
