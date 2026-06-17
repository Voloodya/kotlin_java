package com.kotlin.java;

import com.kotlin.java.concurrency.java.ExecutorServiceCompletableFuture;
import com.kotlin.java.concurrency.java.ExecutorServiceFuture;
import com.kotlin.java.kotlin.InfixKt;
import com.kotlin.java.kotlin.extend.JoinKt;
import com.kotlin.java.kotlin.extend.StringPropertyKt;
import com.kotlin.java.travelingsalesman.Result;
import com.kotlin.java.travelingsalesman.SolverImpl;
import com.kotlin.java.travelingsalesman.TravellingSPInstance;

import java.util.List;

import static com.kotlin.java.kotlin.FunctionKt.greetUser;
import static com.kotlin.java.kotlin.strings.JoinFunctions.joinToString;


public class KotlinJavaApplication {

    static void main(String[] args) {

        int[][] distances = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        TravellingSPInstance tsp = new TravellingSPInstance(distances, distances.length);

        ExecutorServiceFuture future = new ExecutorServiceFuture();
        ExecutorServiceCompletableFuture completableFutureService = new ExecutorServiceCompletableFuture();

        List<Result> resultsFromFuture = future.execute(tsp, List.of((tspI) -> SolverImpl.solveBruteForce(tspI), (tspI) -> SolverImpl.solveGenetic(tspI)));

        greetUser();

        List<Integer> list = List.of(1, 2, 3, 4, 5);

        System.out.println(joinToString(list));

        System.out.println(joinToString(list, "/", "("));

        System.out.println(JoinKt.joinToString(list, ",", "{"));

        List<String> listString = List.of("a", "b", "c", "d", "f");

        System.out.println(JoinKt.joinToStr(listString, ",", "{", "}"));


        System.out.println(StringPropertyKt.getLastChar("Kotlin?!"));

        var infixFunResult = InfixKt.myTo(1, "one");

        System.out.println(infixFunResult);
    }
}
