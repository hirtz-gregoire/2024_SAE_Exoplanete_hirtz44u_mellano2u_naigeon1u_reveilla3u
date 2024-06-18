package tools;

public class ColorTool {

    public static int[] getTabColor(int colorInteger) {
        int[] res = new int[3];

        res[0] = colorInteger & 0xff ; // Blue
        res[1] = ( colorInteger & 0xff00 ) >> 8 ; // Green
        res[2] = ( colorInteger & 0xff0000) >> 16; // Red

        return res;
    }

}
