package indi.mat.work.environment;


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

public class K8sEnvironmentPostProcessor implements EnvironmentPostProcessor {
    public K8sEnvironmentPostProcessor() {
    }

    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator var4 = propertySources.iterator();

        while(var4.hasNext()) {
            PropertySource<?> propertySource = (PropertySource)var4.next();
            if (propertySource.getName().contains("application.yml") || propertySource.getName().contains("application.properties")) {
                Map<String, OriginTrackedValue> source = (Map)propertySource.getSource();
                Map<String, OriginTrackedValue> newConfig = new HashMap(source);
                OriginTrackedValue applicationName = (OriginTrackedValue)source.get("spring.application.name");
                if (applicationName != null && ((OriginTrackedValue)source.get("spring.application.name")).getValue() != null) {
                    if (!newConfig.containsKey("management.endpoint.health.probes.enabled")) {
                        newConfig.put("management.endpoint.health.probes.enabled", OriginTrackedValue.of(true));
                    }

                    if (!newConfig.containsKey("management.metrics.tags.application")) {
                        newConfig.put("management.metrics.tags.application", OriginTrackedValue.of(applicationName));
                    }

                    if (!newConfig.containsKey("management.metrics.distribution.slo.http.server.requests")) {
                        newConfig.put("management.metrics.distribution.slo.http.server.requests", OriginTrackedValue.of("1ms,5ms,10ms,50ms,100ms,200ms,500ms,1s,5s"));
                    }

                    this.setInclude(newConfig);
                    source = Collections.unmodifiableMap(newConfig);
                    PropertySource<?> newPropertySource = new OriginTrackedMapPropertySource(propertySource.getName(), source);
                    propertySources.replace(propertySource.getName(), newPropertySource);
                    break;
                }

                throw new RuntimeException("please config application name  key -> {spring.application.name} in application.yml or application.properties");
            }
        }

    }

    private void setInclude(Map<String, OriginTrackedValue> newConfig) {
        if (!newConfig.containsKey("management.endpoints.web.exposure.include") && !newConfig.containsKey("management.endpoints.web.exposure.include[0]")) {
            newConfig.put("management.endpoints.web.exposure.include[0]", OriginTrackedValue.of("faq"));
            newConfig.put("management.endpoints.web.exposure.include[1]", OriginTrackedValue.of("info"));
            newConfig.put("management.endpoints.web.exposure.include[2]", OriginTrackedValue.of("health"));
            newConfig.put("management.endpoints.web.exposure.include[3]", OriginTrackedValue.of("prometheus"));
        }
    }
}
