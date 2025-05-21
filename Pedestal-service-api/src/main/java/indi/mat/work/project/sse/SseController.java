package indi.mat.work.project.sse;

import groovyjarjarantlr.collections.Enumerator;
import indi.mat.work.project.util.JsonUtil;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static indi.mat.work.project.util.Constant.JWT_HEADER;

/**
 * @author Mat
 * @version : SseController, v 0.1 2023-02-14 1:47 Yang
 */
@RestController
public class SseController {

    private final static Logger log = LoggerFactory.getLogger(SseController.class);
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 创建连接
     *
     * @date: 2022/3/31 20:51
     */
    @GetMapping(path = "sseConnect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable String userId, HttpServletRequest request) {
        try {
            // 设置超时时间，0表示不过期。默认30秒
            SseEmitter sseEmitter = new SseEmitter(0L);
            // 注册回调
            sseEmitter.onCompletion(() -> {
                System.out.println("completionCallBack " + userId);
            });
            sseEmitter.onError(a -> {
                System.out.println(a.getMessage() + " errorCallBack " + userId);
            });
            sseEmitter.onTimeout(() -> {
                System.out.println("timeoutCallBack " + userId);
            });
            sseEmitterMap.put(userId, sseEmitter);
            return sseEmitter;
        } catch (Exception e) {
            log.info("创建新的sse连接异常，当前用户：{}", userId);
        }
        return null;
    }

    /**
     * 给指定用户发送消息
     *
     * @date: 2022/3/31 21:51
     */
    @GetMapping(path = "sseSend")
    public void sendMessage(String userId, String message) {

        if (sseEmitterMap.containsKey(userId)) {
            try {
                sseEmitterMap.get(userId).send(message);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", userId, e.getMessage());
                sseEmitterMap.remove(userId);
            }
        }
    }
}
