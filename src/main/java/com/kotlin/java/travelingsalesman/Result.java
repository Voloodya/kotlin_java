package com.kotlin.java.travelingsalesman;

public record Result(
        int[] path,
        int distance
) {

    public Result {
        if (path == null || path.length == 0) {
            throw new IllegalArgumentException("Path must not be null or empty");
        }
        path = path.clone();
    }

    @Override
    public int[] path() {
        return path.clone();
    }

    public int[] getPath() {
        return path();
    }

    public int getDistance() {
        return distance;
    }
}
