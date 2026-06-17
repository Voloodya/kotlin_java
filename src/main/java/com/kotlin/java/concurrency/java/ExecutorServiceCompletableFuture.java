package com.kotlin.java.concurrency.java;

import com.kotlin.java.travelingsalesman.Result;
import com.kotlin.java.travelingsalesman.TSPSolver;
import com.kotlin.java.travelingsalesman.TravellingSPInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExecutorServiceCompletableFuture {

    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public List<Result> execute(TravellingSPInstance tspInstance, List<TSPSolver> solvers) {
        if (solvers == null || solvers.isEmpty()) {
            throw new IllegalArgumentException("At least one solver is required");
        }

        Set<CompletableFuture<Result>> futures = solvers.stream()
                .map(solver -> CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                IO.println("[ExecutorServiceCompletableFuture] Отправляем на выполнение solver");
                                return solveTask(tspInstance, solver);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }, executorService))
                //.map(CompletableFuture::join) Блокирующия операция, которая будет срабатывать на каждом элементе
                .collect(Collectors.toSet());

        // Создаем "супер-фьючерс", который завершится, когда выполнятся все задачи из списка
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // Декларативно собираем результат после того, как все задачи закончили работу
        CompletableFuture<List<Result>> allResultFuture = allFuture
                .thenApply(f -> {
                            IO.println("[ExecutorServiceCompletableFuture] Собираем результат после того, как все задачи закончили работу");
                            return futures.stream()
                                    .map(CompletableFuture::join)
                                    .toList();
                        }
                );

        // Дожидаемся итогового списка в главном потоке
        IO.println("[ExecutorServiceCompletableFuture] Дожидаемся итогового списка в главном потоке. Block operation");
        List<Result> results = allResultFuture.join();
        IO.println("[ExecutorServiceCompletableFuture] Все потоки завершили работу");

        // Закрыть потоки
        executorService.shutdown();

        return results;
    }

    private Result solveTask(TravellingSPInstance tspInstance, TSPSolver solver) throws InterruptedException {
        Thread.currentThread().setName("CompletableFuture");
        IO.println("[Thread CompletableFuture] работа потока " + Thread.currentThread().getName());
        Thread.sleep(3000);
        return solver.solve(tspInstance);
    }

}
