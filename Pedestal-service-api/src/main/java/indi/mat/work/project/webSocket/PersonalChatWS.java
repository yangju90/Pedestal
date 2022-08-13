package indi.mat.work.project.webSocket;

import indi.mat.work.project.config.WebSocketConfigurator;
import indi.mat.work.project.util.Constant;
import indi.mat.work.project.util.JsonUtil;
import indi.mat.work.project.websocket.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mat
 * @version 1: PersonalChatWS, v 0.1 2022-06-27 14:55 Yang
 */

@Component
@ServerEndpoint(value = "/websocket/personal/chat", configurator = WebSocketConfigurator.class)
public class PersonalChatWS {

    private final Logger logger = LoggerFactory.getLogger(PersonalChatWS.class);

    private static final Map<String, Session> connectWs = new ConcurrentHashMap<>();

    HttpSession httpSession;

    @OnMessage
    public void onMessage(String message, Session session){
        try {
            System.out.println("fromMesg:" + message);
            Message ms = JsonUtil.jsonToObject(message, Message.class);
            Message userMessage = new Message(false, (String)httpSession.getAttribute(Constant.WS_USER), ms.getMessage());
            System.out.println("toMesg:" + JsonUtil.toJsonString(userMessage));
            connectWs.get(ms.getToName()).getBasicRemote().sendText(JsonUtil.toJsonString(userMessage));
        } catch (IOException e) {
            logger.error(this.httpSession.getAttribute(Constant.WS_USER) + " send Message error!", e);
        }
    }

    @OnOpen
    public void onOpen(Session var1, EndpointConfig var2){
        HttpSession httpSession = (HttpSession) var2.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //存储登陆的对象
        String username = (String)httpSession.getAttribute(Constant.WS_USER);
        connectWs.put(username, var1);

        //将当前在线用户的用户名推送给所有的客户端
        Message sysMessage = new Message(true, null, Arrays.asList(connectWs.keySet().toArray(new String[]{})));
        String message = JsonUtil.toJsonString(sysMessage);
        broadcast(message);
    };

    private void broadcast(String message){
        for(String name : connectWs.keySet()){
            try {
                connectWs.get(name).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error(this.httpSession.getAttribute(Constant.WS_USER) + " send broadcast Message error!", e);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String username = (String) httpSession.getAttribute(Constant.WS_USER);
        //从容器中删除指定的用户
        connectWs.remove(username);
        Message sysMessage = new Message(true, null, Arrays.asList(connectWs.keySet().toArray(new String[]{})));
        String message = JsonUtil.toJsonString(sysMessage);
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }

}
