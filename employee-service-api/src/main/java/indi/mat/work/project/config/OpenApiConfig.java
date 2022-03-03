package indi.mat.work.project.config;

import indi.mat.work.project.util.Constant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springdoc.core.Constants.API_DOCS_URL;

@Configuration
public class OpenApiConfig {

    @Autowired
    private ServletContext context;

    @Value(API_DOCS_URL)
    private String apiDocsUrl;

    private static final String SCHEME_NAME = Constant.JWT_HEADER;
    private static final String SCHEME = Constant.JWT_HEADER;

    @Bean
    public OpenAPI apiDocs() {
        OpenAPI openApi = new CustomOpenAPI()
                .info(new Info().title("Newegg Pda api").description("Newegg Pda api").version("1.0"));
        openApi.addServersItem(newServer("https://GDEV-HOST/employee", "GDEV"))
                .addServersItem(newServer("https://GQC-HOST/employee", "GQC"))
                .addServersItem(newServer("https://PRE-HOST/employee", "PRE"))
                .addServersItem(newServer("https://PRD-HOST/employee", "PRD"));
        addSecurity(openApi);
        return openApi;
    }

    private void addSecurity(OpenAPI openApi) {
        var components = createComponents();
        var securityItem = new SecurityRequirement()
                .addList(SCHEME_NAME)
                .addList(Constant.WAREHOUSE_NO_HEADER)
                .addList(Constant.PID_HEADER)
                .addList(Constant.VERSION_CODE);

        openApi.components(components)
                .addSecurityItem(securityItem);
    }

    private Components createComponents() {
        var components = new Components();
        components.addSecuritySchemes(SCHEME_NAME, createSecurityScheme(SCHEME_NAME, SCHEME_NAME));
        components.addSecuritySchemes(Constant.WAREHOUSE_NO_HEADER, createSecurityScheme(Constant.WAREHOUSE_NO_HEADER, Constant.WAREHOUSE_NO_HEADER));
        components.addSecuritySchemes(Constant.VERSION_CODE, createSecurityScheme(Constant.VERSION_CODE, Constant.VERSION_CODE));
        components.addSecuritySchemes(Constant.PID_HEADER, createSecurityScheme(Constant.PID_HEADER, Constant.PID_HEADER));
        return components;
    }

    private SecurityScheme createSecurityScheme(String schemaName, String name) {
        return new SecurityScheme()
                .name(name)
                .type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
                .scheme(schemaName);
    }

    private Server newServer(String url, String env) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(env);
        return server;
    }

    private class CustomOpenAPI extends OpenAPI {

        @Override
        public synchronized List<Server> getServers() {
            List<Server> servers = super.getServers();
            String defaultUrl = defaultUrl();
            if (StringUtils.isNotBlank(defaultUrl)) {
                Server local = newServer(defaultUrl, "LOCAL");
                if (!servers.contains(local)) {
                    servers.add(0, local);
                }
            }
            return servers;
        }

        private String defaultUrl() {
            Optional<RequestAttributes> requestAttributes = Optional.ofNullable(RequestContextHolder.getRequestAttributes());
            if (requestAttributes.isEmpty()) {
                return null;
            }
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes.get()).getRequest();
            String requestUrl = decode(request.getRequestURL().toString());
            return requestUrl.substring(0, requestUrl.length() - apiDocsUrl.length());
        }

        private String decode(String requestUrl) {
            try {
                return URLDecoder.decode(requestUrl, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                return requestUrl;
            }
        }
    }
}
