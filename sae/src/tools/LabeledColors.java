package tools;

public class LabeledColors {
    static final int TUNDRA
            = colorToRgb(71, 70, 61);

    static final int TAIGA
            = colorToRgb(43, 50, 35);

    static final int FORET_TEMPEREE
            = colorToRgb(59, 66, 43);

    static final int FORET_TROPICALE
            = colorToRgb(46, 64, 34);

    static final int SAVANE
            = colorToRgb(84, 106, 70);

    static final int PRAIRIE
            = colorToRgb(104, 95, 82);

    static final int DESERT
            = colorToRgb(152, 140, 120);

    static final int GLACIER
            = colorToRgb(200, 200, 200);

    static final int EAU_PEU_PROFONDE
            = colorToRgb(49, 83, 100);

    static final int EAU_PROFONDE
            = colorToRgb(12, 31, 47);

    public static int colorToRgb(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

    private LabeledColors() {
    }
}
