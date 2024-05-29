import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;


public class Palette implements Iterable<Color>{

    private Color[] colors;

    public Palette(Color[] colors) {
        this.colors = colors;
    }

    public Color getClosestColor(Color color) {

        Color closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < colors.length; i++) {
            double dist = getDistance(color, colors[i]);
            if (dist < closestDistance) {
                closestDistance = dist;
                closest = colors[i];
            }
        }
        return closest;
    }

    public static double getDistance(Color c1, Color c2) {
        // d(c1, c2) = (R(c1) − R(c2))2 + (G(c1) − G(c2))2 + (B(c1) − B(c2))2
        return Math.pow(c1.getRed() - c2.getRed(), 2) + Math.pow(c1.getGreen() - c2.getGreen(), 2) + Math.pow(c1.getBlue() - c2.getBlue(), 2);
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
