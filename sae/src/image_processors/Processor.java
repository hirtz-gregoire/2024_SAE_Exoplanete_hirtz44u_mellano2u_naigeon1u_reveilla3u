package image_processors;

import java.awt.image.BufferedImage;

public interface Processor {

    /**
     * A set of operations to do on a given image
     * @param image the image to apply the operations
     * @return the altered image
     */
    BufferedImage process(BufferedImage image);
}
