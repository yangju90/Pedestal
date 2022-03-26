package indi.mat.work.db.transmittable;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransmittableThreadLocalTest {

    private static TransmittableThreadLocal<Context> context = new TransmittableThreadLocal<>();

    static class Context {

        String name;

        String value;
    }

    public static void main(String[] args) {
        // Fixed Thread Pool
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        // The thread that generated the task assigns a value to the context
                        Context contextMain = new Context();
                        contextMain.name = String.format("Thread%s name", finalI);
                        String uuid = UUID.randomUUID().toString();
                        contextMain.value = uuid;
                        TransmittableThreadLocalTest.context.set(contextMain);
                        // Submit Tasks
                        for (int j = 1; j <= 10; j++) {
                            System.out.println("Thread" + finalI + " produce task " + uuid);
                            final int tmp = j;
                            Runnable task = () -> {
                                // Subthreads performing tasks
                                Context contextChild = TransmittableThreadLocalTest.context.get();
                                System.out.println(finalI+"--"+Thread.currentThread().getId() + " execute task, name : " + contextChild.name + " value : " + contextChild.value);
                            };
                            // Extra processing to generate the decorated object ttlRunnable
                            Runnable ttlRunnable = TtlRunnable.get(task);
                            executorService.execute(ttlRunnable);
                        }

                    }
            ).start();
        }
    }

}
