package net.scat.lp.springboot.test.controller;

import net.scat.lp.springboot.controller.LPResourceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LPResourceControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void toList() {
    }

    @Test
    public void pageList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lpResource/pageList")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("page", "1").param("pageSize", "10"))
                .andDo(MockMvcResultHandlers.print());
    }
}