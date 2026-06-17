package com.kotlin.java.concurrency.java;

import com.kotlin.java.travelingsalesman.Result;
import com.kotlin.java.travelingsalesman.TSPSolver;
import com.kotlin.java.travelingsalesman.TravellingSPInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
public class StructuredConcurrencyService {

    private final ExecutorService executorService;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public List<Result> execute(TravellingSPInstance tspInstance, List<TSPSolver> solvers) {
        if (solvers == null || solvers.isEmpty()) {
            throw new IllegalArgumentException("At least one solver is required");
        }

        // Закрыть потоки
        executorService.shutdown();

        return null;
    }

    private Result solveTask(TravellingSPInstance tspInstance, ExecutorService executorService, TSPSolver solver) throws InterruptedException {
        Thread.currentThread().setName("StructuredConcurrency");
        IO.println("[StructuredConcurrency] работа потока " + Thread.currentThread().getName());
        Thread.sleep(3000);
        return solver.solve(tspInstance);
    }

}
