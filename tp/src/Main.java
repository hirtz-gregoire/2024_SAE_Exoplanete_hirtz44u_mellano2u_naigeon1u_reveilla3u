import image.ImageHandler;
import image.Palette;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try{
            Color[] colors = {new Color(34, 34, 31), new Color(214, 210, 206), new Color(138, 99, 66), new Color(113, 72, 48), new Color(182, 152, 124)};
            Palette palette = new Palette(colors);
            new ImageHandler().colorReductionDuplicatePicture("tp/ressource/src/img3.png", "tp/ressource/out/img.png", palette);
        }catch (IOException e){
            System.out.println(e);
        }

    }
}
