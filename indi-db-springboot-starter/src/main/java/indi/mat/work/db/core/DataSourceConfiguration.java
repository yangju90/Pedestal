package indi.mat.work.db.core;


import indi.mat.work.db.aop.MethodNameAdvice;
import indi.mat.work.db.filter.CustomDbFilter;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Order(100000)
@EnableJdbcRepositories
@ConditionalOnClass(WebMvcConfigurer.class)
@ConditionalOnProperty(prefix = "indi",name = "enable", matchIfMissing = true)
@Aspect
public class DataSourceConfiguration implements WebMvcConfigurer {

    @Autowired
    DbProperties dbProperties;

    @Bean
    public JdbcTemplateFactory jdbcTemplateFactory() {
        return new JdbcTemplateFactory();
    }

    @Bean
    public Advisor txAdviceAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(dbProperties.getServiceAop());
        return new DefaultPointcutAdvisor(pointcut, new MethodNameAdvice());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DbInterceptor());
    }


    // 让CustomDbFilter是单例的，如果加入网络请求，保证唯一性
    @Bean("CustomDbFilter")
    @ConditionalOnProperty(prefix = "hydra",name = "orm",havingValue = "druid")
    public CustomDbFilter customDbFilter() {
        return new CustomDbFilter();
    }

}
