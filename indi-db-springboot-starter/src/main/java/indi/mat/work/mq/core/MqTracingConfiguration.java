package indi.mat.work.mq.core;

import indi.mat.work.mq.aop.MqMethodAdvice;
import indi.mat.work.mq.filter.MqDruidFilter;
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
 */
@EnableConfigurationProperties({MqProperties.class})
@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
@ConditionalOnProperty(prefix = "hydra",name = "enable",matchIfMissing = true)
@Aspect
public class MqTracingConfiguration implements WebMvcConfigurer {

    @Autowired
    private MqProperties mqProperties;

    @Bean
    @ConditionalOnMissingBean(MqMessageSender.class)
    public MqMessageSender hydraMessageSender() {
        Properties properties = new Properties();
        properties.putAll(mqProperties.getKafkaProducer());
        properties.remove("topic");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());
        return new MqMessageKafkaSender(kafkaProducer,mqProperties);
    }

    @Bean("MqDruidFilter")
    @ConditionalOnProperty(prefix = "hydra",name = "orm",havingValue = "druid")
    public MqDruidFilter mqDruidFilter() {
        return new MqDruidFilter(hydraMessageSender());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MqGatewayRequestHeaderInterceptor());
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(mqProperties.getServiceAop());
        return new DefaultPointcutAdvisor(pointcut, methodNameAdvice() );
    }

    @Bean
    public MqMethodAdvice methodNameAdvice() {
        return new MqMethodAdvice();
    }

}
