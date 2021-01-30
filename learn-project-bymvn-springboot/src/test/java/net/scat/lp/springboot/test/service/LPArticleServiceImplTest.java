package net.scat.lp.springboot.test.service;

import net.scat.lp.springboot.po.LPArticle;
import net.scat.lp.springboot.service.LPArticleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LPArticleServiceImplTest {

    @Autowired
    private LPArticleServiceImpl lpArticleService;

    @Test
    public void add() {
        lpArticleService.add(LPArticle.builder()
                .title("测试文章")
                .content("测试文章内容")
                .labels("测试")
                .build());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getList() {
    }
}