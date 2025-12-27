package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface TaskExecutor {
    Future<?> submit(Runnable task);
    <T> Future<T> submit(Callable<T> task);
    void shutdown();
}
