package image_processors.processors.Blur;

import image_processors.Processor;
import image_processors.processors.DuplicateImageByPixel;

import java.awt.image.BufferedImage;

public class GaussianBlur implements Processor {
    private static final double[][] GAUSSIAN_KERNEL_3X3 = {
            {1 / 16d, 2 / 16d, 1 / 16d},
            {2 / 16d, 4 / 16d, 2 / 16d},
            {1 / 16d, 2 / 16d, 1 / 16d}
    };

    private static final double[][] GAUSSIAN_KERNEL_5X5 = {
            {1 / 256d, 4 / 256d, 6 / 256d, 4 / 256d, 1 / 256d},
            {4 / 256d, 16 / 256d, 24 / 256d, 16 / 256d, 4 / 256d},
            {6 / 256d, 24 / 256d, 36 / 256d, 24 / 256d, 6 / 256d},
            {4 / 256d, 16 / 256d, 24 / 256d, 16 / 256d, 4 / 256d},
            {1 / 256d, 4 / 256d, 6 / 256d, 4 / 256d, 1 / 256d}
    };

    private static final double[][] GAUSSIAN_KERNEL_7X7 = {
            {1 / 64d, 6 / 64d, 15 / 64d, 20 / 64d, 15 / 64d, 6 / 64d, 1 / 64d},
            {6 / 64d, 36 / 64d, 90 / 64d, 120 / 64d, 90 / 64d, 36 / 64d, 6 / 64d},
            {15 / 64d, 90 / 64d, 225 / 64d, 300 / 64d, 225 / 64d, 90 / 64d, 15 / 64d},
            {20 / 64d, 120 / 64d, 300 / 64d, 400 / 64d, 300 / 64d, 120 / 64d, 20 / 64d},
            {15 / 64d, 90 / 64d, 225 / 64d, 300 / 64d, 225 / 64d, 90 / 64d, 15 / 64d},
            {6 / 64d, 36 / 64d, 90 / 64d, 120 / 64d, 90 / 64d, 36 / 64d, 6 / 64d},
            {1 / 64d, 6 / 64d, 15 / 64d, 20 / 64d, 15 / 64d, 6 / 64d, 1 / 64d}
    };


    @Override
    public BufferedImage process(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage blurredImage = new DuplicateImageByPixel().process(image);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double sumRed = 0, sumGreen = 0, sumBlue = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int neighborX = x + kx;
                        int neighborY = y + ky;

                        if (neighborX < 0) neighborX = 0;
                        if (neighborY < 0) neighborY = 0;
                        if (neighborX >= width) neighborX = width - 1;
                        if (neighborY >= height) neighborY = height - 1;

                        int pixel = image.getRGB(neighborX, neighborY);
                        int red = (pixel >> 16) & 0xFF;
                        int green = (pixel >> 8) & 0xFF;
                        int blue = pixel & 0xFF;

                        sumRed += red * GAUSSIAN_KERNEL_3X3[ky + 1][kx + 1];
                        sumGreen += green * GAUSSIAN_KERNEL_3X3[ky + 1][kx + 1];
                        sumBlue += blue * GAUSSIAN_KERNEL_3X3[ky + 1][kx + 1];
                    }
                }

                int newRed = Math.min(255, Math.max(0, (int) sumRed));
                int newGreen = Math.min(255, Math.max(0, (int) sumGreen));
                int newBlue = Math.min(255, Math.max(0, (int) sumBlue));

                int newPixel = (newRed << 16) | (newGreen << 8) | newBlue;
                blurredImage.setRGB(x, y, newPixel);
            }
        }

        return blurredImage;
    }
}