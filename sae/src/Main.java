import image_processors.ImageProcessor;
import image_processors.Processor;
import image_processors.processors.Clusterer;
import image_processors.processors.blur.GaussianBlur;
import image_processors.processors.StepExporter;
import tools.Palette;
import tools.cluster.KMeans;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String entree = "ressource/img/Planete1.jpg";
        String sortie = "ressource/out/Planete1.jpg";
        String format = "png";

        try{
            Color[] colors = {new Color(34, 34, 31), new Color(214, 210, 206), new Color(138, 99, 66), new Color(113, 72, 48), new Color(182, 152, 124)};
            Palette palette = new Palette(colors);

            String[] nameExtension = sortie.split("\\.(?=[^\\.]+$)");
            StepExporter exporter = new StepExporter(nameExtension[0],"." + nameExtension[1], format);

            Processor[] processes = {
                    exporter,
                    new Clusterer(new KMeans(10), Clusterer.CLUSTER_BY_POSITION, exporter, palette),
                    exporter
            };

            ImageProcessor imageProcessor = new ImageProcessor(processes);
            BufferedImage image = ImageIO.read(new File(entree));
            imageProcessor.processImage(image);
            // No need to export the image, as the exporter is already doing so for every step in the process
        }catch (IOException e){
            System.out.println(e);
        }

    }
}