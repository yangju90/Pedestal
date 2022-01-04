package indi.mat.work.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        if(logger.isDebugEnabled()) {
            logger.debug("===========================Request Begin=============================================");
            logger.debug("URI         : {}", request.getURI());
            logger.debug("Method      : {}", request.getMethod());
            logger.debug("Headers     : {}", request.getHeaders());
            logger.debug("Request Body: {}", new String(body, StandardCharsets.UTF_8));
            logger.debug("===========================Request End===============================================");
        }
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        if(logger.isDebugEnabled()) {
            logger.debug("===========================Response Begin============================================");
            logger.debug("Response Status Code: {}", response.getStatusCode());
            logger.debug("Response Status Text: {}", response.getStatusText());
            logger.debug("Response Headers    : {}", response.getHeaders());
            logger.debug("===========================Response End==============================================");
        }
    }
}
