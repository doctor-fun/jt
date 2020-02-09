package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

//拦截controller层

@RestControllerAdvice//通知类
public class SysResultExceptionAop {
    /**
     *统一返回数据SysResult对象status=201
     */
    @ExceptionHandler(RuntimeException.class)//当遇到运行时异常时才执行此方法,
    public Object fail(Exception e, HttpServletRequest request){


        String callback = request.getParameter("callback");
        if(!StringUtils.isEmpty(callback)){
            //用户发起的是JSONP请求
            return new JSONPObject(callback,SysResult.fail(""));
        }
        e.printStackTrace();
        return SysResult.fail("失败了");
    }


    /*
        全局异常处理机制，如果遇到jsonp的跨域访问时
        返回值数据应该经过特殊格式封装
        callback(SysResult.fail())
        应该利用Request对象动态获取callback参数
        2实现业务特殊封装
     */
}
