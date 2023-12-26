package com.lzb;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

/**
 * <br/>
 * Created on : 2023-09-27 20:36
 * @author lizebin
 */
@WebMvcTest
@ExtendWith(SpringExtension.class)
public abstract class BaseControllerUnitTest extends BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    protected void assertResponse(ResultActions perform) throws UnsupportedEncodingException {
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString(Charset.defaultCharset());
        assertJSON(contentAsString);
    }
}
