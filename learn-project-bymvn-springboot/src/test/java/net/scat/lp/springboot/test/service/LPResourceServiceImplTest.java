package net.scat.lp.springboot.test.service;

import lombok.extern.slf4j.Slf4j;
import net.scat.lp.springboot.Application;
import net.scat.lp.springboot.po.LPResource;
import net.scat.lp.springboot.service.LPResourceService;
import net.scat.lp.springboot.service.LPResourceServiceImpl;
import net.scat.lp.springboot.util.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class LPResourceServiceImplTest {
    @Autowired
    private LPResourceService lpResourceService;

    @Test
    public void getList() {
        log.info(JSONUtils.toJSON(lpResourceService.getList(1, 10)));
    }

    @Test
    public void add() {
        LPResource lpResource = LPResource.builder()
                .author("嘟嘟")
                .description("嘟嘟独立博客")
                .title("Spring-Boot干货系列")
                .labels("springboot")
                .url("http://tengj.top/categories/Spring-Boot%E5%B9%B2%E8%B4%A7%E7%B3%BB%E5%88%97/")
                .folderId(0)
                .build();

        lpResourceService.add(lpResource);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}