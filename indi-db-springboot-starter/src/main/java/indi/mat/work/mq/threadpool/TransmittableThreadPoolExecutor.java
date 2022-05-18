package indi.mat.work.mq.threadpool;

import com.alibaba.ttl.TtlRunnable;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public class TransmittableThreadPoolExecutor extends ThreadPoolTaskExecutor {


    @Override
    protected ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        this.setTaskDecorator(new ContextCopyingDecorator());
        return super.initializeExecutor(threadFactory, rejectedExecutionHandler);
    }

    static class ContextCopyingDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            Map<String, String> webThreadContext = MDC.getCopyOfContextMap();
            return TtlRunnable.get(() -> {
                try {
                    MDC.setContextMap(webThreadContext);
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            });
        }
    }
}
