package image_processor.processors;

import image_processor.Processor;

import java.awt.image.BufferedImage;

public class GrayScale implements Processor {

    @Override
    public BufferedImage process(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int[] color = ColorTool.getTabColor(image.getRGB(x, y));

                // int greyScale = (int)(color[2] * 0.114 + color[1] * 0.587 + color[0] * 0.299);
                int greyScale = (color[0]+color[1]+color[2])/3;

                greyScale = greyScale + (greyScale << 8) + (greyScale << 16);
                image.setRGB(x, y, greyScale-255);
            }
        }

        return image;
    }
}
