package image_processors.processors.simpleFilters;

import image_processors.Processor;

import java.awt.image.BufferedImage;

public class DuplicateImageByPixel implements Processor {

    private int filter;

    /**
     * Duplicate the given image
     */
    public DuplicateImageByPixel() {
        this.filter = 0xFFFFFF;
    }

    /**
     * Duplicate the given image and apply a given color mask
     * @param filter The color mask to apply to the copy
     */
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
