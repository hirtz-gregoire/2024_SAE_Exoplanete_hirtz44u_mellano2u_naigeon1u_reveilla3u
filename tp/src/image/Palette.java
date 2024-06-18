package image;

import norms.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;


public class Palette implements Iterable<Color>{

    private Color[] colors;
    private static ColorNorm distanceColor = new NormLab();

    public Palette(Color[] colors) {
        if (colors.length <= 0){
            throw new IllegalArgumentException("colors is empty");
        }
        this.colors = colors;
    }

    public Color getClosestColor(Color color) {

        Color closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < colors.length; i++) {
            double dist = distanceColor.colorDistance(color, colors[i]);

            if (dist < closestDistance) {
                closestDistance = dist;
                closest = colors[i];
            }
        }
        return closest;
    }



    public Color[] getColors() {
        return colors;
    }

    public Color getColor(int index) {
        return colors[index];
    }

    public Iterator<Color> iterator() {
        return Arrays.stream(colors).iterator();
    }
}
