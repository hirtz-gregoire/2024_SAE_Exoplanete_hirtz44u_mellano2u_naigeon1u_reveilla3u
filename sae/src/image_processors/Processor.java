package image_processors;

import java.awt.image.BufferedImage;

public interface Processor {
    BufferedImage process(BufferedImage image);
}
