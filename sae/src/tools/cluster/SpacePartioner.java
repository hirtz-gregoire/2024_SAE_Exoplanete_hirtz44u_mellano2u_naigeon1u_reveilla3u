package tools.cluster;

import java.util.*;

public class SpacePartioner {
    private double eps;
    private Map<double[], List<Integer>> grid;
    public SpacePartioner(double eps) {
        this.eps = eps;
        this.grid = new TreeMap<>(new Comparator<double[]>() {
            @Override
            public int compare(double[] a, double[] b) {
                int len = Math.min(a.length, b.length);
                for (int i = 0; i < len; i++) {
                    int cmp = Double.compare(a[i], b[i]);
                    if (cmp != 0) {
                        return cmp;
                    }
                }
                return Integer.compare(a.length, b.length);
            }
        });
    }

    public void partition(double[][] data) {

        for(int i = 0; i < data.length; i++) {
            double[] key = getGridCellKey(data[i]);

            if (!grid.containsKey(key)) {
                grid.put(key, new ArrayList<>());
            }

            grid.get(key).add(i);
        }
    }

    private double[] getGridCellKey(double[] point) {
        double[] key = new double[point.length];

        for(int i = 0; i < point.length; i++) {
            double gridCoordinate = point[i] - (point[i] % eps);
            key[i] = gridCoordinate;
        }

        return key;
    }

    public List<Integer> getAdjacentPoints(double[][] data, int index) {
        List<Integer> neighbors = new ArrayList<>();
        double[] targetPoint = data[index];
        double[] targetKey = getGridCellKey(targetPoint);

        // Generate all possible adjacent cell keys (including the target cell itself)
        for (double[] offset : generateOffsets(targetKey.length)) {
            double[] adjacentKey = new double[targetKey.length];
            for (int i = 0; i < targetKey.length; i++) {
                adjacentKey[i] = targetKey[i] + offset[i];
            }

            // Add all points from the adjacent cell to the neighbors list
            if (grid.containsKey(adjacentKey)) {
                neighbors.addAll(grid.get(adjacentKey));
            }
        }

        return neighbors;
    }

    private List<double[]> generateOffsets(int dimensions) {
        List<double[]> offsets = new ArrayList<>();
        generateOffsetsRecursive(offsets, new double[dimensions], 0);
        return offsets;
    }

    private void generateOffsetsRecursive(List<double[]> offsets, double[] current, int d) {
        if (d == current.length) {
            offsets.add(current.clone());
        } else {
            current[d] = eps;
            generateOffsetsRecursive(offsets, current, d + 1);
            current[d] = -eps;
            generateOffsetsRecursive(offsets, current, d + 1);
            current[d] = 0.0;
            generateOffsetsRecursive(offsets, current, d + 1);
        }
    }

    public void clear() {
        this.grid.clear();
    }

    public static void main(String[] args) {
        double[][] data = {
                {2.1, 2.1},
                {1.1, 1.1},
                {2.1, 1.1},
                {3.1, 1.1},
                {1.1, 2.1},
                {2.1, 2.1},
                {3.1, 2.1},
                {1.1, 3.1},
                {2.1, 3.1},
                {3.1, 3.1},
                {4.2, 1.2},
                {1.2, 4.2},
                {0.2, 1.2},
                {1.2, 0.2},
        };

        SpacePartioner partitioner = new SpacePartioner(1.0);
        partitioner.partition(data);

        for (Map.Entry<double[], List<Integer>> entry : partitioner.grid.entrySet()) {
            System.out.println("Cell: " + Arrays.toString(entry.getKey()) + " Points: " + entry.getValue());
        }

        // Test getAdjacentPoints
        List<Integer> neighbors = partitioner.getAdjacentPoints(data, 0);

        System.out.println();
        System.out.println("Neighbors of point " + Arrays.toString(data[0]) + ":");
        for (int neighbor : neighbors) {
            System.out.println(neighbor);
        }
    }
}
