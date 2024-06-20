package tools.cluster;

import org.apache.commons.lang3.time.StopWatch;

import javax.swing.plaf.IconUIResource;
import java.util.*;

public class DBSCAN implements Clustering {

    private double eps;
    private int minPts;
    private TreeSet<Integer> visite = new TreeSet<>();
    private SpacePartioner spacePartioner;

    public DBSCAN(double eps, int minPts) {
        this.eps = eps;
        this.minPts = minPts;
        this.spacePartioner = new SpacePartioner(eps);
    }

    @Override
    public int[] cluster(double[][] data) {

        visite.clear();
        spacePartioner.clear();
        spacePartioner.partition(data);

        // on crée d'un liste de labelisation initialiser a -1
        int[] labels = new int[data.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = -1;
        }
        int clusterId = 0;

        for (int i=0; i < data.length; i++) {

            if (visite.contains(i)) {
                continue;
            }

            // recup point voisin
            List<Integer> voisins = regionQuery(data, i);

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
     * méthode d'expansion du cluster
     * @param data
     * @param labels
     * @param pointIndex
     * @param voisins
     * @param clusterId
     */
    private void expandCluster(double[][] data, int[] labels, int pointIndex, List<Integer> voisins, int clusterId) {
        labels[pointIndex] = clusterId;

        int index = 0;
        while (index < voisins.size()) {
            int currentPoint = voisins.get(index);

            if (labels[currentPoint] == 0) {
                labels[currentPoint] = clusterId;
            }

            if (labels[currentPoint] == -1) {
                labels[currentPoint] = clusterId;

                List<Integer> voisins_courant = regionQuery(data, currentPoint);
                if (voisins_courant.size() >= minPts) {
                    for (int i = 0; i < voisins_courant.size(); i++) {
                        if (!visite.contains(voisins_courant.get(i))) {
                            voisins.add(voisins_courant.get(i));
                        }
                    }
                }
            }

            int size = visite.size();
            if(size % 1000 < 10) {
                System.out.println(size);
            }
            visite.add(currentPoint);
            index++;
        }
    }

    /**
     * méthode qui renvoie une liste des points compris autour du point centrale, dans un rayon de taille eps
     * @param data ensemble des points
     * @param point point centrale
     * @return liste des points compris autour du oint centrale
     */
    private List<Integer> regionQuery(double[][] data, int point) {

        List<Integer> voisins = spacePartioner.getAdjacentPoints(data, point);
        for (int i = 0; i < data.length; i++) {
            if (distance(data[point], data[i]) <= eps) {
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