package image_processors.processors;

import image_processors.ImageProcessor;
import image_processors.Processor;

import java.awt.image.BufferedImage;

public class ColorFilter implements Processor {

    private int targetColor;
    public ColorFilter(int targetColor) {
        this.targetColor = targetColor;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                if((pixel & 0xFFFFFF) == (targetColor & 0xFFFFFF)) {
                    image.setRGB(x, y, pixel);
                }
                else {
                    image.setRGB(x, y, 0);
                }
            }
        }

        return image;
    }

    public void setTargetColor(int targetColor) {
        this.targetColor = targetColor;
    }
}
