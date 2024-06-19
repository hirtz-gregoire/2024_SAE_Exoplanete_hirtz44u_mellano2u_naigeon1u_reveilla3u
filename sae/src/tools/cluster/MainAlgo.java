package tools.cluster;

public class MainAlgo {

    public static void main(String[] args) {


        double[][] data = {
                {2.0, 1.0},
                {1.5, 1.8},
                {5.0, 1.0},
                {8.0, 8.0},
                {10.0, 0.6},
                {9.0, 1.0}
        };

        Clustering kMeans = new KMeans(2);
        int[] labels = kMeans.cluster(data);

        for (int i = 0; i < labels.length; i++) {
            System.out.println("Objet " + i + " appartient au cluster " + labels[i]);
        }
    }
}
