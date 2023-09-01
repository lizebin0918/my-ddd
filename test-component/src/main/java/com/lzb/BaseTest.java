package com.lzb;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.mockito.Mockito;

/**
 * <br/>
 * Created on : 2023-03-01 10:31
 * @author lizebin
 */
public abstract class BaseTest extends Mockito implements BaseAssertions {

    private static final ObjectMapper INSTANCE = new JacksonObjectMapper();

    public static class JacksonObjectMapper extends ObjectMapper {

        public JacksonObjectMapper() {
            super();
            //序列化处理
            super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            super.findAndRegisterModules();
            //失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            //反序列化时，属性不存在的兼容处理
            super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            super.findAndRegisterModules();
            super.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
            super.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
            super.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            super.configure(SerializationFeature.INDENT_OUTPUT, true);
        }

    }

    protected void assertJSON(Object o) {
        try {
            Approvals.verify(INSTANCE.writerWithDefaultPrettyPrinter().writeValueAsString(o),
                    new Options().forFile().withExtension(".json"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
