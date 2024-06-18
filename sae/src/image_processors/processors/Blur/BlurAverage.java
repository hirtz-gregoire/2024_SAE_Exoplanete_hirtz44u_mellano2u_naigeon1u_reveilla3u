package image_processors.processors.Blur;

import image_processors.Processor;
import image_processors.processors.DuplicateImageByPixel;

import java.awt.image.BufferedImage;

public class BlurAverage implements Processor {
    private int kernelSize;

    /**
     * Apply a blurring effect on the image by averaging the pixel's colors
     * @param kernelSize
     * @return
     */
    public BlurAverage(int kernelSize) {
        this.kernelSize =  kernelSize;
    }
    public BufferedImage process(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth() ;
        BufferedImage nvImage = new DuplicateImageByPixel().process(image);

        int filterOffset = this.kernelSize / 2;

        // Parcourir chaque pixel de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Initialisation des sommes des couleurs
                int sumRed = 0, sumGreen = 0, sumBlue = 0;
                int count = 0;

                // Parcourir le voisinage 5x5
                for (int ky = -filterOffset; ky <= filterOffset; ky++) {
                    for (int kx = -filterOffset; kx <= filterOffset; kx++) {
                        int neighborX = x + kx;
                        int neighborY = y + ky;

                        // Gestion des bords par réflexion
                        if (neighborX < 0) neighborX = 0;
                        if (neighborY < 0) neighborY = 0;
                        if (neighborX >= width) neighborX = width - 1;
                        if (neighborY >= height) neighborY = height - 1;

                        int pixel = image.getRGB(neighborX, neighborY);
                        sumRed += (pixel >> 16) & 0xFF;
                        sumGreen += (pixel >> 8) & 0xFF;
                        sumBlue += pixel & 0xFF;
                        count++;
                    }
                }

                // Calcul de la moyenne
                int avgRed = sumRed / count;
                int avgGreen = sumGreen / count;
                int avgBlue = sumBlue / count;

                // Recomposition de la couleur
                int newPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;

                // Affectation du pixel flouté
                nvImage.setRGB(x, y, newPixel);
            }
        }

        return nvImage;
    }




}
