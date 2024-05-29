import image.ImageHandler;
import image.Palette;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try{
            Color[] colors = {new Color(70, 37, 30), new Color(79, 94, 38), new Color(140, 177, 192), new Color(36, 48, 84)};
            Palette palette = new Palette(colors);
            new ImageHandler().colorReductionDuplicatePicture("tp/ressource/src/img1.png", "tp/ressource/out/img.png", palette);
        }catch (IOException e){
            System.out.println(e);
        }

    }
}
