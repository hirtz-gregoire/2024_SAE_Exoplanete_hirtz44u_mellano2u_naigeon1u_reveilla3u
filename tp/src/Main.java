import image.ImageHandler;
import image.Palette;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try{
            Color[] colors = {new Color(93, 82, 86), new Color(150, 142, 66), new Color(75,112, 146), new Color(255, 255, 255)};
            Palette palette = new Palette(colors);
            new ImageHandler().colorReductionDuplicatePicture("tp/ressource/src/img1.png", "tp/ressource/out/img.png", palette);
        }catch (IOException e){
            System.out.println(e);
        }

    }
}
