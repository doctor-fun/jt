package com.jt.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.utils.ObejctMapperUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebJSONPController{
    //http://manager.jt.com/web/testJSONP?callback=xxx

/**
 * web端对这个路径的访问，将由ajax自动带上回调函数的相关参数（比如函数名等）这边就要带上相关
 *
 * 返回值应该是callback(json) 符合jsonp语法，利用js的src可以跨域的格式
 * callback是web端在js的ajax定义中的回调函数名称，这样web端和后端知道各自发送接收的回调函数名称是啥
 *
 *
 * web端js发出ajax的的定义
 * <script type="text/javascript">
 * 	$(function(){
 * 		alert("测试访问开始!!!!!")
 * 		$.ajax({
 * 			url:"http://manage.jt.com/web/testJSONP",
 * 			type:"get",				//jsonp只能支持get请求
 * 			dataType:"jsonp",       //dataType表示返回值类型，这个类型会将hello{json}编程{json},方便回调赋值
 * 		这一行  	jsonp: "callback",    //指定参数名称
 * 			jsonpCallback: "hello",  //指定回调函数名称
 * 			success:function (data){   //data经过jQuery封装返回就是json串
 * 				alert(data.id);
 * 				alert(data.name);
 * 				//转化为字符串使用
 * 				//var obj = eval("("+data+")");
 * 				//alert(obj.name);
 *                        }* 		});
 * 	})
 */
// @RequestMapping("/web/testJSONP")
//
//    public String jsonP(String callback){
//        ItemDesc itemDesc=new ItemDesc();
//        itemDesc.setItemId(100L).setItemDesc("JSONP调用成功!!");
//        String json= ObejctMapperUtils.toJson(itemDesc);
//        return callback + "("+json+")";
//
//
//    }
@RequestMapping("/web/testJSONP")
public JSONPObject jsonP(String callback){
    ItemDesc itemDesc=new ItemDesc();
    itemDesc.setItemId(100L).setItemDesc("JSONP调用成功!!");
    return new JSONPObject(callback,itemDesc);//callback是前端js约定的回调函数名，itemDesc会被转化为Json，
    // 然后两个一起拼接成call(json)


}
}
