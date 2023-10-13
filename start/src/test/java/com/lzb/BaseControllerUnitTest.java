package com.lzb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.approvaltests.JsonApprovals;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.json.JSONException;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

/**
 * <br/>
 * Created on : 2023-09-27 20:36
 * @author lizebin
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public abstract class BaseControllerUnitTest extends BaseTest {

    @Autowired
    protected MockMvc mockMvc;

    protected void assertResponse(ResultActions perform) throws UnsupportedEncodingException {
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        Options options = new Options();
        options = options.withComparator((actualFile, exceptFile) -> {
            if (!exceptFile.exists()) {
                return FileApprover.approveTextFile(actualFile, exceptFile);
            }
            try {
                String actualString = Files.readString(Paths.get(actualFile.getAbsolutePath()));
                String expectString = Files.readString(Paths.get(exceptFile.getAbsolutePath()));
                JSONAssert.assertEquals(expectString, actualString, JSONCompareMode.NON_EXTENSIBLE);
                return VerifyResult.SUCCESS;
            } catch (IOException | JSONException e) {
                return VerifyResult.FAILURE;
            }
        });
        JsonApprovals.verifyJson(contentAsString, options);
    }
}
