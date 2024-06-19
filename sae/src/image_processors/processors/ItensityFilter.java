package image_processors.processors;

import image_processors.Processor;
import tools.ColorTool;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class ItensityFilter implements Processor {

    private double intensity;

    /**
     * Increase the intensity of the given image by 75%
     */
    public ItensityFilter() {
        this.intensity = 75;
    }

    /**
     * Modify the intensity of the given image by [intensity]%
     * @param intensity the intensity change in % (can be negative)
     */
    public ItensityFilter(double intensity) {
        this.intensity = intensity;
    }
    @Override
    public BufferedImage process(BufferedImage image) {

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int[] rbgValues = ColorTool.getTabColor(image.getRGB(x, y));
                rbgValues[0] = (int)round(rbgValues[0] + intensity/100 * (255 - rbgValues[0]));
                rbgValues[1] = (int)round(rbgValues[1] + intensity/100 * (255 - rbgValues[1]));
                rbgValues[2] = (int)round(rbgValues[2] + intensity/100 * (255 - rbgValues[2]));

                image.setRGB(x, y, image.getRGB(x, y));
            }
        }

        return image;
    }
}
