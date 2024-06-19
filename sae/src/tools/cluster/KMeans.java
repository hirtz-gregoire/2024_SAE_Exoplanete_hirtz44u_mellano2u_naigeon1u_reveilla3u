package tools.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Clustering {

    private int nbrGroup; // nombre de clusters souhaités

    public KMeans(int nbrG) {
        this.nbrGroup = nbrG;
    }

    /**
     * Applique l'algorithme de clustering K-Means sur les données fournies.
     *
     * @param data Un tableau en deux dimensions représentant les objets à classifier.
     * @return Un tableau en une dimension contenant le numéro de cluster pour chaque objet.
     *
     *
     * Algorithme 1 : K-Means
     *
     * Entrées : ng ≥ 0 nombre de groupes
     * Entrées : D = {di }i données
     * Résultat : Centroïdes mis à jour
     * Initialisation des centroïdes
     * 1 pour i ∈ [0, ng] faire
     * 2 ci ← random(D)
     * Boucle principale
     * 3 tant que (non(ni)) faire
     *      Initialisation Groupes
     *      4 pour i ∈ [0, ng] faire
     *      5 Gi ← ∅
     *      Construction des Groupes
     *      6 pour d ∈ D faire
     *      7 k ← indiceCentroidePlusProche(d, {ci }i )
     *      8 Gk ← Gk ∪ d
     *      Mise à jour des centroïdes
     *      9 pour i ∈ [0, ng] faire
     *      10 ci ← barycentre(Gi)
     */
    @Override
    public int[] cluster(double[][] data) {
        //initialisation des Objects et caractéristiques
        int nbrObjects = data.length;
        int nbrCaract = data[0].length;
        Random random = new Random();

        //création d'un tableau à deux dimensions de centroids avec pour taille nbrGroup et nbrCaract
        double[][] centroids = new double[nbrGroup][nbrCaract];

        //création d'un tableau qui contiendra les différents clusters
        int[] labels = new int[nbrObjects];
        boolean converged = false;

        // Initialisation des centroïdes
        for (int i = 0; i < nbrGroup; i++) {
            centroids[i] = data[random.nextInt(nbrObjects)];
        }

        while (!converged) {
            converged = true;
            // Initialisation des groupes
            List<List<double[]>> groups = new ArrayList<>();
            for (int i = 0; i < nbrGroup; i++) {
                groups.add(new ArrayList<>());
            }

            // Construction des groupes
            for (int i = 0; i < nbrObjects; i++) {
                int centroidIndex = indexOfCentroid(data[i], centroids);
                groups.get(centroidIndex).add(data[i]);
                labels[i] = centroidIndex;
            }

            // Mise à jour des centroïdes
            for (int i = 0; i < nbrGroup; i++) {
                double[] newCentroid = calculateCentroid(groups.get(i), nbrCaract);
                if (!centroidsEqual(centroids[i], newCentroid)) {
                    centroids[i] = newCentroid;
                    converged = false;
                }
            }
        }

        return labels;
    }

    /**
     * Trouve l'indice du centroïde le plus proche d'un point de donnée.
     *
     * @param dataPoint Le point de donnée.
     * @param centroids Les centroïdes existants.
     * @return L'indice du centroïde le plus proche.
     */
    private int indexOfCentroid(double[] dataPoint, double[][] centroids) {
        int Index = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < centroids.length; i++) {
            double distance = euclideanDistance(dataPoint, centroids[i]);
            if (distance < minDistance) {
                minDistance = distance;
                Index = i;
            }
        }
        return Index;
    }

    /**
     * Calcule la distance euclidienne entre deux points.
     *
     * @param a Le premier point.
     * @param b Le deuxième point.
     * @return La distance euclidienne entre les deux points.
     */
    private double euclideanDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Calcule le centroïde d'un groupe de points.
     *
     * @param group    Le groupe de points.
     * @param nbrCarat Le nombre de caractéristiques de chaque point.
     * @return Le centroïde du groupe.
     */
    private double[] calculateCentroid(List<double[]> group, int nbrCarat) {
        double[] centroid = new double[nbrCarat];
        for (double[] dataPoint : group) {
            for (int i = 0; i < nbrCarat; i++) {
                centroid[i] += dataPoint[i];
            }
        }
        for (int i = 0; i < nbrCarat; i++) {
            centroid[i] /= group.size();
        }
        return centroid;
    }

    /**
     * Compare deux centroïdes pour vérifier s'ils sont égaux.
     *
     * @param a Le premier centroïde.
     * @param b Le deuxième centroïde.
     * @return true si les centroïdes sont égaux, sinon false.
     */
    private boolean centroidsEqual(double[] a, double[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
