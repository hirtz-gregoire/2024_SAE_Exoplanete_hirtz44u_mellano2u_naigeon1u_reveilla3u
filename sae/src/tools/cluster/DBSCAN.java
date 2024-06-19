package tools.cluster;

import java.util.ArrayList;
import java.util.List;

public class DBSCAN implements clustering {

    private double eps;
    private int minPts;

    public DBSCAN(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    @Override
    public int[] cluster(double[][] data) {

        // on crée d'un liste de labelisation initialiser a -1
        int[] labels = new int[data.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = -1;
        }
        int clusterId = 0;

        for (int i=0; i < data.length; i++) {
            if (labels[i] != -1) {
                continue;  // si label != -1, alors point déjà traité
            }
            // recup point voisin
            List<Integer> voisins = regionQuery(data, data[i]);

            // si minPts non atteint, alors labellisation BorderPoint
            if (voisins.size() < minPts) {
                labels[i] = 0;
            }
            // sinon nouveau CorePoint
            else {
                clusterId++;
                expandCluster(data, labels, i, voisins, clusterId);
            }
        }

        return labels;
    }

    /**
     * 
     * @param data
     * @param labels
     * @param pointIndex
     * @param voisins
     * @param clusterId
     */
    private void expandCluster(double[][] data, int[] labels, int pointIndex, List<Integer> voisins, int clusterId) {
        labels[pointIndex] = clusterId;

        List<Integer> copie_voisins = new ArrayList<>(voisins);
        int index = 0;
        while (index < copie_voisins.size()) {
            int currentPoint = copie_voisins.get(index);
            if (labels[currentPoint] == 0) {
                labels[currentPoint] = clusterId;
            }
            if (labels[currentPoint] == -1) {
                labels[currentPoint] = clusterId;
                List<Integer> currentNeighbors = regionQuery(data, data[currentPoint]);
                if (currentNeighbors.size() >= minPts) {
                    copie_voisins.addAll(currentNeighbors);
                }
            }
            index++;
        }
    }

    /**
     * méthode qui renvoie une liste des points compris autour du point centrale, dans un rayon de taille eps
     * @param data ensemble des points
     * @param point point centrale
     * @return liste des points compris autour du oint centrale
     */
    private List<Integer> regionQuery(double[][] data, double[] point) {
        List<Integer> voisins = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (distance(point, data[i]) <= eps) {
                voisins.add(i);
            }
        }
        return voisins;
    }

    /**
     * méthode de calcule de distance entre 2 point sur un plan 2D
     * @param point1
     * @param point2
     * @return distance entre point1 et point2 en double
     */
    private double distance(double[] point1, double[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }
}
