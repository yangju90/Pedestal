package indi.mat.work.project.controller.async;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Mat
 * @version : AsyncController, v 0.1 2023-02-14 0:51 Yang
 */

@RestController
public class AsyncController {
    // 存放监听某个Id的长轮询集合
    // 线程同步结构
    public static ConcurrentHashMap<String, DeferredResult<String>> watchRequests = new ConcurrentHashMap<>();

    public final static long TIME_OUT = 10000;

    /**
     * 设置监听
     */
    @GetMapping(path = "watch/{id}")
    public DeferredResult<String> watch(@PathVariable String id) {
        // 延迟对象设置超时时间
        DeferredResult<String> deferredResult = new DeferredResult<>(TIME_OUT);
        // 异步请求完成时移除 key，防止内存溢出
        deferredResult.onCompletion(() -> {
            watchRequests.remove(id);
        });
        // 注册长轮询请求
        watchRequests.put(id, deferredResult);
        return deferredResult;
    }

    /**
     * 变更数据
     */
    @GetMapping(path = "publish/{id}")
    public String publish(@PathVariable String id) {
        // 数据变更 取出监听ID的所有长轮询请求，并一一响应处理
        if (watchRequests.containsKey(id)) {
            DeferredResult<String> deferredResult = watchRequests.get(id);
            deferredResult.setResult("我更新了" + new Date());
        }
        return "success";
    }


    @GetMapping("/callable")
    public Callable<String> callable() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 主线程start");

        Callable<String> callable = () -> {
            System.out.println(Thread.currentThread().getName() + " 子子子线程start");
            TimeUnit.SECONDS.sleep(5); //模拟处理业务逻辑，话费了5秒钟
            System.out.println(Thread.currentThread().getName() + " 子子子线程end");

            // 这里稍微小细节一下：最终返回的不是Callable对象，而是它里面的内容
            return "hello world";
        };

        System.out.println(Thread.currentThread().getName() + " 主线程end");
        return callable;
    }


    @GetMapping("/WebAsyncTask")
    public WebAsyncTask<String> webAsyncTask() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 主线程start");

        Callable<String> callable = () -> {
            System.out.println(Thread.currentThread().getName() + " 子子子线程start");
            TimeUnit.SECONDS.sleep(5); //模拟处理业务逻辑，话费了5秒钟
            System.out.println(Thread.currentThread().getName() + " 子子子线程end");

            return "hello world";
        };

        // 采用WebAsyncTask 返回 这样可以处理超时和错误 同时也可以指定使用的Excutor名称
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(7000, callable);
        // 注意：onCompletion表示完成，不管你是否超时、是否抛出异常，这个函数都会执行的
        webAsyncTask.onCompletion(() -> System.out.println("程序[正常执行]完成的回调"));

        // 这两个返回的内容，最终都会放进response里面去===========
        webAsyncTask.onTimeout(() -> "程序[超时]的回调");
        // 备注：这个是Spring5新增的
        webAsyncTask.onError(() -> "程序[出现异常]的回调");


        System.out.println(Thread.currentThread().getName() + " 主线程end");
        return webAsyncTask;
    }

}
