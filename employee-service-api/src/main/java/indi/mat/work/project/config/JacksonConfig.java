package indi.mat.work.project.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_FLOAT_AS_INT;

@Configuration
public class JacksonConfig{

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
        objectMapper.configure(ACCEPT_FLOAT_AS_INT, false);
                ObjectMapper objectMapper = builder.createXmlMapper(false)
                //compatibility C# date pattern /Date(1335205592410)/
//                .deserializerByType(Date.class,new JacksonCustomerDateJsonDeserializer())
                .simpleDateFormat(DateStringConstant.fulltime)
                .build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        objectMapper.setConfig(objectMapper.getDeserializationConfig()
                .with(JsonReadFeature.ALLOW_TRAILING_COMMA)
                .with(JsonWriteFeature.ESCAPE_NON_ASCII));
        
        return objectMapper;
    }

}
