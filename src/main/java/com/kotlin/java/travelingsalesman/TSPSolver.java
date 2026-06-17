package com.kotlin.java.travelingsalesman;

@FunctionalInterface
public interface TSPSolver {
    Result solve(TravellingSPInstance tspInstance);
}
