package tools.cluster;

public interface Clustering {

    /**
     * Applique l'algorithme de clustering sur les données fournies.
     *
     * @param data Un tableau en deux dimensions représentant les objets à classifier.
     * @return Un tableau en une dimension contenant le numéro de cluster pour chaque objet.
     */
    int[] cluster(double[][] data);
}
