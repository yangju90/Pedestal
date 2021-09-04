@Configuration
public class JacksonConfig{

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
        objectMapper.configure(ACCEPT_FLOAT_AS_INT, false);
        return objectMapper;
    }

}
