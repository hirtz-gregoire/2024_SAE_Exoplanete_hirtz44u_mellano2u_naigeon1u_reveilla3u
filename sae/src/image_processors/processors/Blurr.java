package image_processors.processors;

import tools.blurr.BlurrConvolution;
import image_processors.Processor;

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