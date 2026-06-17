package com.kotlin.java.travelingsalesman;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SolverImpl {

    private static final int GENETIC_ITERATIONS = 5_000;


    // 1. ПОЛНЫЙ ПЕРЕБОР (Brute Force)
    public static Result solveBruteForce(TravellingSPInstance tsp) {
        Objects.requireNonNull(tsp, "tsp must not be null");
        int n = tsp.numberOfCities();
        int[] bestPath = new int[n];
        final int[] minDistance = {Integer.MAX_VALUE};

        recursiveFind(tsp, new boolean[n], 0, 1, 0, new int[n], bestPath, minDistance);
        return new Result(bestPath, minDistance[0]);
    }

    private static void recursiveFind(TravellingSPInstance tsp, boolean[] visited, int curr, int count,
                                      int cost, int[] path, int[] bestPath, int[] min) {
        visited[curr] = true;
        path[count - 1] = curr;

        if (count == tsp.numberOfCities()) {
            int total = cost + tsp.getDistance(curr, 0);
            if (total < min[0]) {
                min[0] = total;
                System.arraycopy(path, 0, bestPath, 0, path.length);
            }
        } else {
            for (int i = 0; i < tsp.numberOfCities(); i++) {
                if (!visited[i]) {
                    recursiveFind(tsp, visited, i, count + 1, cost + tsp.getDistance(curr, i), path, bestPath, min);
                }
            }
        }
        visited[curr] = false; // Backtracking
    }

    // 2. ГЕНЕТИЧЕСКИЙ АЛГОРИТМ (Упрощенный набросок)
    public static Result solveGenetic(TravellingSPInstance tsp) {
        Objects.requireNonNull(tsp, "tsp must not be null");
        int n = tsp.numberOfCities();
        int[] bestPath = new int[n];
        int minDistance = Integer.MAX_VALUE;

        // Удерживаем старт в городе 0: это убирает эквивалентные циклические сдвиги.
        List<Integer> cities = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            cities.add(i);
        }

        for (int i = 0; i < GENETIC_ITERATIONS; i++) {
            Collections.shuffle(cities);
            int[] candidatePath = new int[n];
            candidatePath[0] = 0;
            for (int j = 0; j < cities.size(); j++) {
                candidatePath[j + 1] = cities.get(j);
            }

            int currentDist = calculatePathDistance(tsp, candidatePath);
            if (currentDist < minDistance) {
                minDistance = currentDist;
                bestPath = candidatePath.clone();
            }
        }
        return new Result(bestPath, minDistance);
    }

    private static int calculatePathDistance(TravellingSPInstance tsp, int[] path) {
        int d = 0;
        for (int i = 0; i < path.length - 1; i++) {
            d += tsp.getDistance(path[i], path[i + 1]);
        }
        d += tsp.getDistance(path[path.length - 1], path[0]);
        return d;
    }
}
