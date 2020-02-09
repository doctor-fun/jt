package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class MessageController {
    @Value("${server.port}")
    private  String port;
    @RequestMapping("/getPort")
    //要去动态获取真实服务器端口号
    public String getPort(){
        return port;
    }

}
