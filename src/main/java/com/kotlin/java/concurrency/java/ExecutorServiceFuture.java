package com.kotlin.java.concurrency.java;

import com.kotlin.java.travelingsalesman.Result;
import com.kotlin.java.travelingsalesman.TSPSolver;
import com.kotlin.java.travelingsalesman.TravellingSPInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ExecutorServiceFuture {

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final AtomicBoolean running = new AtomicBoolean(true);

    public List<Result> execute(TravellingSPInstance tspInstance, List<TSPSolver> solvers) {
        if (solvers == null || solvers.isEmpty()) {
            throw new IllegalArgumentException("At least one solver is required");
        }

        List<Result> results = new ArrayList<>();
        Set<Future<Result>> futures = solvers.stream()
                .map(solver -> {
                    try {
                        IO.println("[ExecutorServiceFuture] Отправляем на выполнение solver");
                        return solveTask(tspInstance, executorService, solver);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        for (Future<Result> future : futures) {
            Result result = null;
            try {
                IO.println("Get Result from Future. Block operation");
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            results.add(result);
        }

        // Закрыть потоки
        executorService.shutdown();

        return results;
    }

    private Future<Result> solveTask(TravellingSPInstance tspInstance, ExecutorService executorService, TSPSolver solver) throws InterruptedException {
        Thread.currentThread().setName("Future");
        IO.println("[Thread Future] работа потока " + Thread.currentThread().getName());
        Thread.sleep(3000);
        return executorService.submit(() -> solver.solve(tspInstance));
    }

}
