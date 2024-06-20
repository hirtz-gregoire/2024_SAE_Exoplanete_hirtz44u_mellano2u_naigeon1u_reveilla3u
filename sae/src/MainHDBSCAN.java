import image_processors.ImageProcessor;
import image_processors.Processor;
import image_processors.processors.*;
import image_processors.processors.blur.BilateralFilter;
import tools.Palette;
import tools.cluster.DBSCAN;
import tools.cluster.KMeans;
import tools.paletteDetection.CostBasedKMeans;
import tools.paletteDetection.ExpoColorCountCost;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainHDBSCAN {

    public static void main(String[] args) {

        String entree = "sae/ressource/img/Planete10.jpg";
        String sortie = "sae/ressource/out/Planete10.jpg";
        String format = "png";

        try{
            Color[] colors = {new Color(34, 34, 31), new Color(214, 210, 206), new Color(138, 99, 66), new Color(113, 72, 48), new Color(182, 152, 124)};
            Palette palette = new Palette(colors);

            String[] nameExtension = sortie.split("\\.(?=[^\\.]+$)");
            StepExporter exporter = new StepExporter(nameExtension[0],"." + nameExtension[1], format);

            Processor[] processes = {
                    exporter,
                    new BilateralFilter(7, 100, 100),
                    exporter,
                    new ColorReduction(new CostBasedKMeans(new ExpoColorCountCost(0.04, 0)), palette),
                    exporter,
                    new Clusterer(new DBSCAN(1, 5), Clusterer.CLUSTER_BY_COLOR_AND_POSITION, exporter, palette),
                    exporter,
            };

            ImageProcessor imageProcessor = new ImageProcessor(processes);
            BufferedImage image = ImageIO.read(new File(entree));
            imageProcessor.process(image);
            // No need to export the image, as the exporter is already doing so for every step in the process
        }catch (IOException e){
            System.out.println(e);
        }

    }
}