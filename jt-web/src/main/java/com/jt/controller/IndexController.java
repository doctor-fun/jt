package com.jt.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
//    //实现页面的通用跳转
////    @RequestMapping("/page/{moduleName}")
////    public String itemAdd(@PathVariable String moduleName){
////        return moduleName;
////    }
}
