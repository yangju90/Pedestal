package com.newegg.logistics.hydra.core;


import com.newegg.logistics.hydra.aop.HydraMethodAdvice;
import com.newegg.logistics.hydra.orm.druid.filter.HydraDruidFilter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * Hydra追踪配置。
 * <p>适用于Newegg Hydra。
 * @author vc80
 * @since 2021-11-23 15:21:06
 */
@EnableConfigurationProperties({HydraProperties.class})
@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
@ConditionalOnProperty(prefix = "hydra",name = "enable",matchIfMissing = true)
@Aspect
public class HydraTracingConfiguration  implements WebMvcConfigurer {

    @Autowired
    private HydraProperties hydraProperties;

    @Bean
    @ConditionalOnMissingBean(HydraMessageSender.class)
    public HydraMessageSender hydraMessageSender() {
        Properties properties = new Properties();
        properties.putAll(hydraProperties.getKafkaProducer());
        properties.remove("topic");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());
        return new HydraMessageKafkaSender(kafkaProducer,hydraProperties);
    }

    @Bean("HydraDruidFilter")
    @ConditionalOnProperty(prefix = "hydra",name = "orm",havingValue = "druid")
    public HydraDruidFilter hydraDruidFilter() {
        return new HydraDruidFilter(hydraMessageSender());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HydraGatewayRequestHeaderInterceptor());
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(hydraProperties.getServiceAop());
        return new DefaultPointcutAdvisor(pointcut, methodNameAdvice() );
    }

    @Bean
    public HydraMethodAdvice methodNameAdvice() {
        return new HydraMethodAdvice();
    }

}
