package image_processors;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    Processor[] myProcessors;

    public ImageProcessor(Processor[] processors) {
        myProcessors = processors;
    }

    public BufferedImage processImage(BufferedImage image) {
        for(Processor processor : myProcessors) {
            image = processor.process(image);
        }
        return image;
    }
}
