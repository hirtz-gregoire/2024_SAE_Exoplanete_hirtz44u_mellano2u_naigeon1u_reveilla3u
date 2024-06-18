package image_processors.processors;

import image_processors.Processor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StepExporter implements Processor {

    private String filename, extension, format;
    private int stepCount = 0;

    public StepExporter(String filename, String extension, String format) {
        this.filename = filename;
        this.extension = extension;
        this.format = format;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        try {
            ImageIO.write(image, format, new File(filename + "_step_" + (++stepCount) + extension));
        } catch (IOException e) {
            System.err.println("Step exporter: IOException occurred at step " + stepCount);
            System.err.println("message: " + e.getMessage());
        }

        return image;
    }

    public int getStepCount() {
        return stepCount;
    }
}
