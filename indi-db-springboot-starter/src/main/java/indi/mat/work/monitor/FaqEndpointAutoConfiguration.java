package indi.mat.work.monitor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaqEndpointAutoConfiguration {
    public FaqEndpointAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    public FaqEndpoint faqEndpoint() {
        return new FaqEndpoint();
    }
}
