package image_processors.processors;

import image_processors.Processor;
import tools.ColorTool;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class IntensityFilter implements Processor {

    private double intensity;

    /**
     * Increase the intensity of the given image by 75%
     */
    public IntensityFilter() {
        this.intensity = 75;
    }

    /**
     * Modify the intensity of the given image by [intensity]%
     * @param intensity the intensity change in % (can be negative)
     */
    public IntensityFilter(double intensity) {
        this.intensity = intensity;
    }
    @Override
    public BufferedImage process(BufferedImage image) {

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int[] rgbValues = ColorTool.getTabColor(image.getRGB(x, y));
                rgbValues[0] = (int)round(rgbValues[0] + intensity/100 * (255 - rgbValues[0]));
                rgbValues[1] = (int)round(rgbValues[1] + intensity/100 * (255 - rgbValues[1]));
                rgbValues[2] = (int)round(rgbValues[2] + intensity/100 * (255 - rgbValues[2]));

                image.setRGB(x, y, ColorTool.getColorIntFromRGB(rgbValues[0], rgbValues[1], rgbValues[2]));
            }
        }

        return image;
    }
}
