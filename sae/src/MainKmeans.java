import image_processors.ImageProcessor;
import image_processors.Processor;
import image_processors.processors.*;
import image_processors.processors.blur.BilateralFilter;
import image_processors.processors.blur.CircleBlurAverage;
import image_processors.processors.blur.GaussianBlur;
import image_processors.processors.simpleFilters.ColorFilter;
import image_processors.processors.simpleFilters.DuplicateImageByPixel;
import image_processors.processors.simpleFilters.IntensityFilter;
import tools.Palette;
import tools.cluster.KMeans;
import tools.paletteDetection.CostBasedKMeans;
import tools.paletteDetection.ExpoColorCountCost;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainKmeans {

    public static void main(String[] args) {

        String entree = "ressource/img/Planete1.jpeg";
        String sortie = "ressource/out/Planete1.jpeg";
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
                    new ColorReduction(new CostBasedKMeans(new ExpoColorCountCost(0.13, 3)), palette),
                    exporter,
                    new CallbackProcessor(image -> { // Extract ecosystems from biomes
                        DuplicateImageByPixel duplicator = new DuplicateImageByPixel();
                        ColorFilter filter = new ColorFilter(0);
                        Clusterer clusterer = new Clusterer(new KMeans(10), Clusterer.CLUSTER_BY_POSITION, true);

                        IntensityFilter intensityFilter = new IntensityFilter();
                        BackgroundLayerer backgroundLayerer = new BackgroundLayerer(intensityFilter.process(duplicator.process(image)));

                        for(Color color : palette) { // Foreach biome, find the ecosystem
                            BufferedImage copy = duplicator.process(image);
                            filter.setTargetColor(color.getRGB());
                            copy = filter.process(copy);
                            copy = clusterer.process(copy);

                            // Need to add back the background as it got turned black
                            copy = backgroundLayerer.process(copy);
                            exporter.process(copy);
                        }

                        return image;
                    }),
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