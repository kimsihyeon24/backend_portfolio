import service.TransferService;
import java.util.concurrent.*;

public class ConcurrencyTestRunner {
    public static void run(TransferService transferService) throws InterruptedException {
        String from = "B";
        String to = "A";

        int amount = 100;
        int tasks = 1000;
        int threads = 50;

        ExecutorService es = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {
            es.submit(() -> {
                try {
                    start.await(); // 여기서 다 같이 출발!
                    transferService.transfer(from, to, amount);
                } catch (Exception e) {
                    // 실패도 중요한 데이터라 일단 카운트만 해도 됨
                     System.out.println("fail: " + e.getMessage());
                } finally {
                    done.countDown();
                }
            });
        }

        long t0 = System.currentTimeMillis();
        start.countDown();       // 동시에 출발
        done.await();            // 다 끝날 때까지 대기
        long t1 = System.currentTimeMillis();

        es.shutdown();
        System.out.println("동시성 테스트 끝. ms=" + (t1 - t0));
    }
}
