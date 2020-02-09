package com.jt.controller;
import com.jt.vo.SysResult;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 查询user表的全部json展现 findAll
	 */
	@RequestMapping("/findAll")
	public List<User> findAll(){
		return userService.findAll();

	}

	/**
	 * JSONP实现跨域访问
	 * 返回值对象:JSONObject对象（callback,data）
	 * 业务返回值对象SysResult:SysResult
	 * 业务判断需求:true用户已经存在false 可以使用
	 *
	 *
	 * 参数分析
	 * param:用户需要校验的数据
	 * type:1用户名2手机号3邮箱
	 */

	//url有个参数callback=...
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback
	)
	{
		JSONPObject jsonpObject=null;
		try {
			boolean flag = userService.checkUser(param, type);

			SysResult result=SysResult.ok(flag);

			jsonpObject = new JSONPObject(callback, result);
		}catch (Exception e)
		{
			e.printStackTrace();
			jsonpObject=new JSONPObject(callback,SysResult.fail("没找到"));
		}
		return jsonpObject;
	}


	/**
	 * jsonP跨域调用
	 * 前端页面发起cookie查询，希望能拿到用户登录信息，没有就删除cookie,如果有就将cookie对应的信息从redis里面找出来给它
	 *
	 *
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket, HttpServletResponse httpServletResponse,String callback){
		String userJson = jedisCluster.get(ticket);
		JSONPObject jsonpObject=null;
		if(StringUtils.isEmpty(userJson)){
			//删除用户的cookie记录
			Cookie cookie=new Cookie("JT_TICKET","");
			cookie.setMaxAge(0);
				cookie.setPath("/");
				cookie.setDomain("jt.com");
				httpServletResponse.addCookie(cookie);
				jsonpObject=new JSONPObject(callback,SysResult.fail("登录失败"));
		}else {
			//表示用户的json数据成功获取
			SysResult result=SysResult.ok(userJson	);//为userJson封装成SysResult
			jsonpObject=new JSONPObject(callback,result);
		}


		return jsonpObject;



	}



}
