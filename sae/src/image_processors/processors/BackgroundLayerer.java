package image_processors.processors;

import image_processors.Processor;

import java.awt.image.BufferedImage;

public class BackgroundLayerer implements Processor {

    BufferedImage background;

    public BackgroundLayerer(BufferedImage background) {
        this.background = background;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                if((pixel & 0xFFFFFF) == 0) {
                    image.setRGB(x, y, background.getRGB(x, y));
                }
                else {
                    image.setRGB(x, y, pixel);
                }
            }
        }

        return image;
    }
}
