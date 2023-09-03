package com.lzb;


import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.mockito.Mockito;

/**
 * <br/>
 * Created on : 2023-03-01 10:31
 * @author lizebin
 */
public abstract class BaseTest extends Mockito implements BaseAssertions {

    private static final ObjectMapper INSTANCE = JsonMapper.builder()
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true)
            .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .defaultLocale(Locale.CHINA)
            .defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
            .addModule(new JavaTimeModule())
            .defaultDateFormat(new StdDateFormat().withColonInTimeZone(true))
            .build();

    protected void assertJSON(Object o) {
        try {
            Approvals.verify(INSTANCE.writerWithDefaultPrettyPrinter().writeValueAsString(o),
                    new Options().forFile().withExtension(".json"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
