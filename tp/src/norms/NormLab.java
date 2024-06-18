package norms;

import tools.Rgb2Lab;
import java.awt.*;

public class NormLab implements ColorNorm{


    @Override
    public double colorDistance(Color c1, Color c2) {
        int[] c1_lab = Rgb2Lab.rgb2lab(c1);
        int[] c2_lab = Rgb2Lab.rgb2lab(c2);

        double C1 = Math.sqrt(Math.pow(c1_lab[1], 2) + Math.pow(c1_lab[2], 2));
        double C2 = Math.sqrt(Math.pow(c2_lab[1], 2) + Math.pow(c2_lab[2], 2));

        double deltaL = c1_lab[0] - c2_lab[0];
        double deltaC = C1 - C2;
        double deltaH = Math.sqrt(Math.pow(c1_lab[1] - c2_lab[1], 2) + Math.pow(c1_lab[2] - c2_lab[2], 2) - Math.pow(deltaC, 2));

        double Sc = 1.0 + 0.045 * C1;
        double Sh = 1.0 + 0.015 * C1;

        double deltaE = Math.sqrt(Math.pow(deltaL,2) + Math.pow(deltaC/Sc,2) + Math.pow(deltaH/Sh,2));

        return deltaE;
    }
}
