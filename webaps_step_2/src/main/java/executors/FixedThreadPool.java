package executors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class FixedThreadPool {
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // execute Runnable
        execute();

        // submit Runnable
        submitRunnable();

        // submit Callable
        submitCallable();

        // invokeAny
        invokeAny();

        // invokeAll
        invokeAll();

        executorService.shutdown();
    }

    private static Set<Callable<String>> prepareCallables() {
        Set<Callable<String>> callables = new HashSet<>();
        callables.add(() -> "Task 1");
        callables.add(() -> "Task 2");
        callables.add(() -> "Task 3");
        callables.add(() -> "Task 4");
        callables.add(() -> "Task 5");
        callables.add(() -> "Task 6");
        return callables;
    }

    private static void invokeAll() throws InterruptedException, ExecutionException {
        Set<Callable<String>> callables = prepareCallables();
        List<Future<String>> futures = executorService.invokeAll(callables);
        for (Future<String> future : futures) {
            System.out.println("future.get = " + future.get());
        }
    }

    private static void invokeAny() throws ExecutionException, InterruptedException {
        Set<Callable<String>> callables = prepareCallables();
        String result = executorService.invokeAny(callables);
        System.out.println("result = " + result);
    }

    private static void submitCallable() throws ExecutionException, InterruptedException {
        Future futureResult = executorService.submit(() -> {
            System.out.println("Callable 1");
            return "Callable 1 Result";
        });
        System.out.println("futureResult = " + futureResult.get());
    }

    private static void submitRunnable() throws ExecutionException, InterruptedException {
        Future futureResult = executorService.submit(() -> System.out.println("Runnable 2"));
        System.out.println("null if success: " + futureResult.get());
        System.out.println("is done? " + futureResult.isDone());
    }

    private static void execute() {
        executorService.execute(() -> System.out.println("Runnable 1"));
    }
}
