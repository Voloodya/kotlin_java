package com.kotlin.java.travelingsalesman;

public record TravellingSPInstance(
        int[][] distances,
        int numberOfCities

) {

    public TravellingSPInstance {
        if (distances == null || distances.length == 0) {
            throw new IllegalArgumentException("Distance matrix must not be null or empty");
        }
        if (numberOfCities != distances.length) {
            throw new IllegalArgumentException("numberOfCities must match distance matrix size");
        }
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == null || distances[i].length != numberOfCities) {
                throw new IllegalArgumentException("Distance matrix must be square");
            }
        }
        distances = copyMatrix(distances);
    }

    @Override
    public int[][] distances() {
        return copyMatrix(distances);
    }

    public int getDistance(int from, int to) {
        validateIndex(from);
        validateIndex(to);
        return distances[from][to];
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    private void validateIndex(int city) {
        if (city < 0 || city >= numberOfCities) {
            throw new IllegalArgumentException("City index out of bounds: " + city);
        }
    }

    private static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }
}
