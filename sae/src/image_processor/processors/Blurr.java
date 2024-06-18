package image_processor.processors;

import blurr.BlurrConvolution;
import image_processor.Processor;

import java.awt.image.BufferedImage;

public class Blurr implements Processor {

    BlurrConvolution convolution;

    public Blurr(BlurrConvolution convolutionType) {
        this.convolution = convolutionType;
    }


    @Override
    public BufferedImage process(BufferedImage image) {
        return convolution.blurr(image);
    }
}
