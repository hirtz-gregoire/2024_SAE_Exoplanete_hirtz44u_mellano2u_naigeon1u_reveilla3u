package tools;

public class ColorTool {

    public static int[] getTabColor(int colorInteger) {
        int[] res = new int[3];

        res[0] = ( colorInteger & 0xff0000) >> 16; // Red
        res[1] = ( colorInteger & 0xff00 ) >> 8 ; // Green
        res[2] = colorInteger & 0xff ; // Blue

        return res;
    }

    public static int getColorIntFromRGB(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

}
