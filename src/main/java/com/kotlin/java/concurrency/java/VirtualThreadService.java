package com.kotlin.java.concurrency.java;

import com.kotlin.java.travelingsalesman.Result;
import com.kotlin.java.travelingsalesman.TSPSolver;
import com.kotlin.java.travelingsalesman.TravellingSPInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VirtualThreadService {

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final AtomicBoolean running = new AtomicBoolean(true);

    public List<Result> execute(TravellingSPInstance tspInstance, List<TSPSolver> solvers) {
        if (solvers == null || solvers.isEmpty()) {
            throw new IllegalArgumentException("At least one solver is required");
        }

        List<CompletableFuture<Result>> completableFutures = new ArrayList<>();


        // Закрыть потоки
        executorService.shutdown();

        return null;
    }

    private Future<Result> solveTask(TravellingSPInstance tspInstance, ExecutorService executorService, TSPSolver solver) throws InterruptedException {
        Thread.currentThread().setName("VirtualThread");
        IO.println("[VirtualThread] работа потока " + Thread.currentThread().getName());
        Thread.sleep(3000);
        return executorService.submit(() -> solver.solve(tspInstance));
    }
}
