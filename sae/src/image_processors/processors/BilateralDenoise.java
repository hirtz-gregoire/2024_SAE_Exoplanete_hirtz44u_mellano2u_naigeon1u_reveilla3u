package image_processors.processors;

import image_processors.Processor;

import java.awt.image.BufferedImage;

public class BilateralDenoise implements Processor {

    // Spacial difference influence
    private double sigma_d;
    // Color intensity difference influence
    private double sigma_r;
    public BilateralDenoise(double sigma_d, double sigma_r) {
        this.sigma_d = sigma_d;
        this.sigma_r = sigma_r;
    }
    @Override
    public BufferedImage process(BufferedImage image) {
        //                                                                           Entrée : image, sigma_d (sigma de distance), sigma_r (sigma de la plage de valeurs)


        //Pour chaque pixel (x, y) dans l'image :
        //    w_sum = 0.0
        //    i_sum = 0.0
        //    Pour chaque pixel (i, j) dans la fenêtre de voisinage centrée sur (x, y) :
        //        distance_squared = (x - i)^2 + (y - j)^2
        //        intensity_difference = image[x, y] - image[i, j]
        //
        //        # Calcul du poids gaussien basé sur la distance spatiale
        //        w_spatial = exp(-distance_squared / (2 * sigma_d^2))
        //
        //        # Calcul du poids gaussien basé sur la différence d'intensité
        //        w_intensity = exp(-intensity_difference^2 / (2 * sigma_r^2))
        //
        //        # Poids combiné
        //        w = w_spatial * w_intensity
        //
        //        # Mise à jour des sommes pondérées
        //        w_sum += w
        //        i_sum += w * image[i, j]
        //
        //    # Mise à jour du pixel filtré
        //    image_filtree[x, y] = i_sum / w_sum
        //
        //Sortie : image_filtree
        return null;
    }
}
