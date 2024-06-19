package tools;

import java.awt.*;

public enum ColorLabels {

    TUNDRA(71, 70, 61),
    TAIGA(43, 50, 35),
    FORET_TEMPEREE(59, 66, 43),
    FORET_TROPICALE(46, 64, 34),
    SAVANE(84, 106, 70),
    PRAIRIE(104, 95, 82),
    DESERT(152, 140, 120),
    GLACIER(200, 200, 200),
    EAU_PEU_PROFONDE(49, 83, 100),
    EAU_PROFONDE(12, 31, 47);
    private Color color;
    private ColorLabels(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }
    public Color getColor() {
        return color;
    }
    public static void main(String[] args) {
        for (ColorLabels colorLabels : ColorLabels.values()) {
            System.out.println("Nom: " + colorLabels.name() + ", Couleur: " + colorLabels.getColor());
        }
    }
}
