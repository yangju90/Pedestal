package indi.mat.work.db.core;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import indi.mat.work.db.filter.CustomDbFilter;
import indi.mat.work.db.util.DbConfigUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.dialect.SqlServerDialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTemplateFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Map<String,DataAccessStrategy> dataAccessStrategyMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static NamedParameterJdbcTemplate getJdbc(String dbName){
        if(!StringUtils.hasText(dbName)){
            throw new RuntimeException("dbName is null "+dbName);
        }
        if(isContain(dbName + "JDBC_TEMPLATE")){
            return getBean(dbName + "JDBC_TEMPLATE" ,NamedParameterJdbcTemplate.class);
        }else{
            DataSource dataSource = getDataSource(dbName);
            NamedParameterJdbcTemplate jdbcTemplate = createJdbc(dataSource);
            registerBean(dbName+ "JDBC_TEMPLATE" , jdbcTemplate, NamedParameterJdbcTemplate.class);
            return jdbcTemplate;
        }
    }

    public static NamedParameterJdbcTemplate createJdbc(DataSource dataSource){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return namedParameterJdbcTemplate;
    }

    public static DruidDataSource getDataSource(String dbName){
        if(!StringUtils.hasText(dbName)){
            throw new RuntimeException("dbName is null "+dbName);
        }
        if(isContain(dbName + "DRUID_DATA_SOURCE")){
            return getBean(dbName+ "DRUID_DATA_SOURCE", DruidDataSource.class);
        }else{
//            DruidDataSource dataSource = createDataSource(DbConfigUtil.fillUrl(getBean(dbName, DataSourceConfig.class)));
            DruidDataSource dataSource = createDataSource(DbConfigUtil.fillUrl(getBean(DataSourceConfig.class)));
            registerBean(dbName+ "DRUID_DATA_SOURCE", dataSource , DruidDataSource.class);
            return dataSource;
        }
    }

    private static DruidDataSource createDataSource(DataSourceConfig config){
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName(config.getDriverClassName());
        source.setUrl(config.getUrl());
        source.setUsername(config.getUsername());
        source.setPassword(config.getPassword());
        source.setMaxActive(config.getMaxActive());
        source.setMinIdle(config.getMinIdle());
        source.setMaxWait(config.getMaxWait());
        source.setValidationQuery("SELECT 1");
        source.setNotFullTimeoutRetryCount(-1);
        source.setConnectionErrorRetryAttempts(-1);
        source.setBreakAfterAcquireFailure(false);
        if(isContain("CustomDbFilter")){
            List<Filter> filterList = new ArrayList<>();
            filterList.add(getBean(CustomDbFilter.class));
            source.setProxyFilters(filterList);
        }
        return source;
    }


    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static void registerBean(String beanName,Object obj,Class requiredType){
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getAutowireCapableBeanFactory();
        //动态注册bean.
        defaultListableBeanFactory.registerSingleton(beanName,obj);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过class获取Bean.
    public static boolean isContain(String name){
        return getApplicationContext().containsBean(name);
    }
}
