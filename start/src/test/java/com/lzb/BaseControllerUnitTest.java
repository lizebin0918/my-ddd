package com.lzb;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

}
