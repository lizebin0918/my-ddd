package com.lzb.adapter.config;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {

        JsonMapper mapper = JsonMapper.builder()
                .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true)
                .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                // BigDecimal写成字符串
                .configure(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 枚举写成字符串
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
                // 时间序列化
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // 时区
                .defaultLocale(Locale.CHINA)
                .defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
                .addModule(new JavaTimeModule())
                .build();

        // 输入非空字段
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 只显示有的字段
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

}