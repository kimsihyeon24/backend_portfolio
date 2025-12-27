package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPollExecutor implements TaskExecutor {

    private final ExecutorService executor;

    public FixedThreadPollExecutor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }
    @Override
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }
}
