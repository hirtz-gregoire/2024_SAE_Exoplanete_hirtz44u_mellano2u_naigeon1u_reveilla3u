package image_processors;

import java.awt.image.BufferedImage;

public class ImageProcessor implements Processor{
    Processor[] myProcessors;

    /**
     * Class used to apply multiple effects on an image
     * @param processors list of effects to apply to the given image
     */
    public ImageProcessor(Processor[] processors) {
        myProcessors = processors;
    }

    public BufferedImage process(BufferedImage image) {
        for(Processor processor : myProcessors) {
            image = processor.process(image);
        }
        return image;
    }
}
