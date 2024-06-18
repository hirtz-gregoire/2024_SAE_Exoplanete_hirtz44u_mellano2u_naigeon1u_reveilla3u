package tools;

public class ColorTool {

    public static int[] getTabColor(int c) {
        int[] res = new int[3];

        res[0] = c & 0xff ; // Blue
        res[1] = ( c & 0xff00 ) >> 8 ; // Green
        res[2] = ( c & 0xff0000) >> 16; // Red

        return res;
    }

}
