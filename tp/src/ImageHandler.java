import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.imageio.*;

public class ImageHandler {

    public static void main(String[] args) {

        try{
            new ImageHandler().duplicateImageByPixel("tp/ressource/src/img1.png", "tp/ressource/out/img.png");
        }catch (IOException e){
            System.out.println(e);
        }

    }


    public void duplicateImage(String in, String out) throws IOException {
        // Load the input picture
        BufferedImage bufferedImage = ImageIO.read(new File(in));

        // copy input to output
        ImageIO.write(bufferedImage, "png", new File(out));
    }

    public void duplicateImageByPixel(String in, String out) throws IOException {
        duplicateImageByPixel(in, out, 0xFFFFFF);
    }

    public void duplicateImageByPixel(String in, String out, int filter) throws IOException {
        // Load the input picture and create the vessel
        BufferedImage image = ImageIO.read(new File(in));
        BufferedImage vessel = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        // Copy the image into the vessel and save the result
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                vessel.setRGB(x, y, image.getRGB(x, y) & filter);
            }
        }

        // save picture
        ImageIO.write(vessel, "png", new File(out));
    }

    public void grayscaleDuplicateImage(String in, String out) throws IOException {
        // Load the input picture and create the vessel
        BufferedImage image = ImageIO.read(new File(in));
        BufferedImage vessel = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        // Copy the image into the vessel and save the result
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int[] color = ColorTool.getTabColor(image.getRGB(x, y));

                // int greyScale = (int)(color[2] * 0.114 + color[1] * 0.587 + color[0] * 0.299);
                int greyScale = (color[0]+color[1]+color[2])/3;

                greyScale = greyScale + (greyScale << 8) + (greyScale << 16);
                vessel.setRGB(x, y, greyScale-255);
            }
        }

        // save picture
        ImageIO.write(vessel, "png", new File(out));
    }
}
