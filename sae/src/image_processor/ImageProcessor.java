package image_processor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageProcessor {
    ArrayList<Processor> myProcessors;

    public ImageProcessor(ArrayList<Processor> processors) {
        myProcessors = processors;
    }

    public BufferedImage processImage(BufferedImage image) {
        for(Processor processor : myProcessors) {
            image = processor.process(image);
        }
        return image;
    }
}
