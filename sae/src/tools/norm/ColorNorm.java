package tools.norm;

import java.awt.*;

public interface ColorNorm {

    /**
     * A norm to compare the distance between two colors in the color space
     * @param c1 The first color to compare
     * @param c2 The second color to compare
     * @return The distance between two colors in the color space
     */
    double colorDistance(Color c1, Color c2);
}
