package net.scat.lp.springboot.controller;

import com.github.pagehelper.PageInfo;
import net.scat.lp.springboot.po.LPResource;
import net.scat.lp.springboot.service.LPResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    public PageInfo<LPResource> pageList(int page, int pageSize) {
        return new PageInfo<>(lpResourceService.getList(page, pageSize));
    }
}
