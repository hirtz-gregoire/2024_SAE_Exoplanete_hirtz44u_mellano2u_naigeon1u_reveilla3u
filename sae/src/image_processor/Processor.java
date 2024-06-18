package image_processor;

import java.awt.image.BufferedImage;

public interface Processor {
    BufferedImage process(BufferedImage image);
}
