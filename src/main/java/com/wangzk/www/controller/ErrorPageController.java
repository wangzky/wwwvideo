package com.wangzk.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * PackageName: com.wangzk.www.controller
 * ClassName: ErrorPageController
 * Description:
 * date: 2020/1/10 15:44
 *
 * @author bufou-wangzk
 */
@Controller
public class ErrorPageController {

    @RequestMapping("error-404")
    public ModelAndView toPage404(){
        ModelAndView mv = new ModelAndView("404/index");
        return mv;
    }
    @RequestMapping("error-400")
    public String toPage400(){
        return "error/error-400";
    }
    @RequestMapping("error-500")
    public String toPage500(){
        return "error/error-500";
    }
}
