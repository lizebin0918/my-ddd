package com.lzb.component.utils.json;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

@UtilityClass
@Slf4j
public class JsonUtils {

    private static final ObjectMapper INSTANCE = JsonMapper.builder()
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
            .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .defaultLocale(Locale.CHINA)
            .defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
            .addModule(new JavaTimeModule())
            .defaultDateFormat(new StdDateFormat().withColonInTimeZone(true))
            .build();

    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    @Nullable
    public static <T> String toJSONString(T value) {
        try {
            return INSTANCE.writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }



    public static <T> String toJSONString(ObjectMapper objectMapper, T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T json2JavaBean(String content, Class<T> valueType) {
        try {
            return INSTANCE.readValue(content, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T json2JavaBean(String content, TypeReference<T> typeReference) {
        try {
            return INSTANCE.readValue(content, typeReference);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e.getMessage());
        }
    }

    public static <T> List<T> json2Array(String json, Class<T> valueTypeRef) {

        JavaType javaType = INSTANCE.getTypeFactory().constructParametricType(List.class, valueTypeRef);
        List<T> objectList = Collections.emptyList();
        try {
            return INSTANCE.readValue(json, javaType);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return objectList;
    }
    

    /**
     * json to Map
     * @param content
     * @return
     */
    public static Map<String, Object> json2Map(String content) {
        try {
            return INSTANCE.readValue(content, Map.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static <T> T readValue(ObjectMapper snakeCaseInstance, String content, TypeReference<T> valueTypeRef) {
        T result;
        try {
            result = snakeCaseInstance.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}