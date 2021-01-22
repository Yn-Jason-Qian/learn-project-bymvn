package net.scat.lp.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import net.scat.lp.springboot.po.LPResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @GetMapping("/index")
    public ModelAndView mainIndex(ModelAndView view) {
        List<LPResource> learnList =new ArrayList<LPResource>();
//        LPResource bean =new LPResource("官方参考文档","Spring Boot Reference Guide","http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#getting-started-first-application");
//        learnList.add(bean);
//        bean =new LPResource("官方SpriongBoot例子","官方SpriongBoot例子","https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples");
//        learnList.add(bean);
//        bean =new LPResource("龙国学院","Spring Boot 教程系列学习","http://www.roncoo.com/article/detail/125488");
//        learnList.add(bean);
//        bean =new LPResource("嘟嘟MD独立博客","Spring Boot干货系列 ","http://tengj.top/");
//        learnList.add(bean);
//        bean =new LPResource("后端编程嘟","Spring Boot教程和视频 ","http://www.toutiao.com/m1559096720023553/");
//        learnList.add(bean);
//        bean =new LPResource("程序猿DD","Spring Boot系列","http://www.roncoo.com/article/detail/125488");
//        learnList.add(bean);
//        bean =new LPResource("纯洁的微笑","Sping Boot系列文章","http://www.ityouknow.com/spring-boot");
//        learnList.add(bean);
//        bean =new LPResource("CSDN——小当博客专栏","Sping Boot学习","http://blog.csdn.net/column/details/spring-boot.html");
//        learnList.add(bean);
//        bean =new LPResource("梁桂钊的博客","Spring Boot 揭秘与实战","http://blog.csdn.net/column/details/spring-boot.html");
//        learnList.add(bean);
//        bean =new LPResource("林祥纤博客系列","从零开始学Spring Boot ","http://412887952-qq-com.iteye.com/category/356333");
//        learnList.add(bean);
        view.addObject("learnList", learnList);
        view.setViewName("index");

        log.info("");
        return view;
    }
}
