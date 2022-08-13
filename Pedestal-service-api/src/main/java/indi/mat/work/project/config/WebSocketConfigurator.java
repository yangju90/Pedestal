package indi.mat.work.project.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author Mat
 * @version : WebSocketConfigurator, v 0.1 2022-06-27 23:14 Yang
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        // obtain HttpSession
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
