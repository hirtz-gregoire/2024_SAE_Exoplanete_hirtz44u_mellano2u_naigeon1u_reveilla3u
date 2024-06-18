package norm;

import java.awt.*;

public class NormSquareMean implements ColorNorm {

    @Override
    public double colorDistance(Color c1, Color c2) {
        // d(c1, c2) = (R(c1) − R(c2))2 + (G(c1) − G(c2))2 + (B(c1) − B(c2))2
        return Math.pow(c1.getRed() - c2.getRed(), 2) + Math.pow(c1.getGreen() - c2.getGreen(), 2) + Math.pow(c1.getBlue() - c2.getBlue(), 2);
    }
}
