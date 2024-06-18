package tools.blurr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlurrAverage {

    public BufferedImage flouMoyenne(BufferedImage image) {
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        int height = image.getHeight();
        int width = image.getWidth() ;
        BufferedImage nvImage = new BufferedImage(width,height,5);

            for(int y=0; y<height;y++) {
                for(int x=0;x<width;x++) {
                    int color = image.getRGB(x,y);
                    int sumRed = 0, sumGreen = 0, sumBlue = 0;

                    // Parcourir le voisinage 3x3
                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
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
                        }
                    }

                    // Calcul de la moyenne
                    int avgRed = sumRed / 9;
                    int avgGreen = sumGreen / 9;
                    int avgBlue = sumBlue / 9;

                    // Recomposition de la couleur
                    int newPixel = (avgRed << 16) | (avgGreen << 8) | avgBlue;

                    // Affectation du pixel flouté
                    nvImage.setRGB(x, y, newPixel);

                }
            }

        return nvImage;
    }

}
