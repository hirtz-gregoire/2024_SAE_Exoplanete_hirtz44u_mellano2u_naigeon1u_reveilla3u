package image_processors.processors;

import image_processors.Processor;

import java.awt.image.BufferedImage;

public class DuplicateImageByPixel implements Processor {

    private int filter;

    public DuplicateImageByPixel() {
        this.filter = 0xFFFFFF;
    }
    public DuplicateImageByPixel(int filter) {
        this.filter = filter;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // Clone the image into the vessel and save the result
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                res.setRGB(x, y, image.getRGB(x, y) & filter);
            }
        }

        return res;
    }
}
