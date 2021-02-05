package net.scat.lp.springboot.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageParams;
import net.scat.lp.springboot.model.PageParam;
import net.scat.lp.springboot.model.PageResult;
import net.scat.lp.springboot.po.LPResource;
import net.scat.lp.springboot.service.LPResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/lpResource")
public class LPResourceController {

    @Autowired
    private LPResourceService lpResourceService;

    @GetMapping("toList")
    public ModelAndView toList(ModelAndView view) {
        view.setViewName("/lpResource/list");
        return view;
    }

    @GetMapping("pageList")
    @ResponseBody
    public PageResult<LPResource> pageList(PageParam param) {
        List<LPResource> list = lpResourceService.getList(param.getPage(), param.getRows());
        return new PageResult<>(list, 10, 10, 1);
    }
}
