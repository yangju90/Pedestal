package indi.mat.work.project.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mat
 * @version : RestTemplateConfig, v 0.1 2022-01-04 15:58 Yang
 */

@Configuration
public class RestTemplateConfig {

    @Primary
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }


    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(){
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
            }
        };
    }
}
