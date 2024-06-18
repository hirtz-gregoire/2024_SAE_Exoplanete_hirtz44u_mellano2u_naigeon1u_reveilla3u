import image_processors.ImageProcessor;
import image_processors.Processor;
import image_processors.processors.ColorReduction;
import tools.Palette;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String entree = "sae/ressource/img/Planete1.jpg";
        String sortie = "sae/ressource/out/Planete1.jpg";
        String format = "png";

        try{
            Color[] colors = {new Color(34, 34, 31), new Color(214, 210, 206), new Color(138, 99, 66), new Color(113, 72, 48), new Color(182, 152, 124)};
            Palette palette = new Palette(colors);

            Processor[] processes = {
                    (Processor) new ColorReduction(palette),
            };

            ImageProcessor imageProcessor = new ImageProcessor(processes);

            BufferedImage image = ImageIO.read(new File(entree));
            image = imageProcessor.processImage(image);
            ImageIO.write(image, format, new File(sortie));
        }catch (IOException e){
            System.out.println(e);
        }

    }
}