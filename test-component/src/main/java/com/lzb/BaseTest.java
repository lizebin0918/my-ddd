package com.lzb;


import org.approvaltests.JsonJacksonApprovals;
import org.mockito.Mockito;

/**
 * <br/>
 * Created on : 2023-03-01 10:31
 * @author lizebin
 */
public abstract class BaseTest extends Mockito implements BaseAssertions {

    protected void assertJSON(Object o) {
        JsonJacksonApprovals.verifyAsJson(o);
    }

}
