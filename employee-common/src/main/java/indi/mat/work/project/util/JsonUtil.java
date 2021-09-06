package indi.mat.work.project.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import indi.mat.work.project.exception.EmployeeException;

public class JsonUtil {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        // 格式化输出
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        // 允许序列化空的POJO类
//        // （否则会抛出异常）
//        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        // 把java.util.Date, Calendar输出为数字（时间戳）
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        // 在遇到未知属性的时候不抛出异常
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        // 强制JSON 空字符串("")转换为null对象值:
//        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//
        // 在JSON中允许C/C++ 样式的注释(非标准，默认禁用)
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 允许没有引号的字段名（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号（非标准）
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        // 强制转义非ASCII字符
//        mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
//        // 将内容包裹为一个JSON属性，属性名由@JsonRootName注解指定
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);


        //反序列化的时候如果多了其他属性,不抛出异常  
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //如果是空对象的时候,不抛异常  
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        //序列化的时候序列对象的所有属性  
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式  
//         mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//         mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.setTimeZone(TimeZone.getDefault());
        MAPPER.setConfig(MAPPER.getDeserializationConfig()
                .with(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .with(JsonReadFeature.ALLOW_TRAILING_COMMA)
                .with(JsonReadFeature.ALLOW_MISSING_VALUES)
                .with(JsonWriteFeature.ESCAPE_NON_ASCII));

//        // 允许出现特殊字符和转义符
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        // 允许出现单引号
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//        // 字段保留，将null值转为""
//        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator,
//                                  SerializerProvider serializerProvider)
//                    throws IOException {
//                jsonGenerator.writeString("");
//            }
//        });
    }


    public static String toJsonString(Object object) {
        String ans = "";
        try {
            ans = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EmployeeException("JsonUtil toJsonString Error");
        }

        return ans;
    }
public static String toJsonString(Object o, boolean pretty) throws JsonProcessingException {
        if(pretty) {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } else {
            return MAPPER.writeValueAsString(o);
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    public static <T> T jsonToObject(String json, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return MAPPER.readValue(json, valueTypeRef);
    }

    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> keyClazz, Class<V> valueClazz) throws JsonProcessingException {
        return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(Map.class, keyClazz, valueClazz));
    }

    public static <T> List<T> jsonToList(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionLikeType(List.class, clazz));
    }

}
