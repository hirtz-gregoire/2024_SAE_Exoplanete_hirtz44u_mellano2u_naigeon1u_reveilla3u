package image_processors.processors;

import image_processors.Processor;

import javax.security.auth.callback.Callback;
import java.awt.image.BufferedImage;

public class CallbackProcessor implements Processor {

    Processor callback;

    public CallbackProcessor(Processor callback) {
        this.callback = callback;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        return callback.process(image);
    }
}
