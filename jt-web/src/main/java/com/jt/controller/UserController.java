package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * check 是否校验服务的提供者
     * loadBalance="负载均衡的配置，如果有多个服务提供者
     * //不设置则默认使用随机策略
     * round是轮询策略
     * //
     */
    @Reference(check=false )
    private DubboUserService userService;//第三方的远程接口

    @Autowired
    private JedisCluster jedisCluster;
    /**
     * 1.实现通用页面跳转
     */
    @RequestMapping("/{moduleName}")
    public String module(@PathVariable String moduleName){

        return moduleName;

    }


    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user ){
                userService.saveUser(user);
                return SysResult.ok();

    }


    /**
     * 根据用户名和密码查询
     *  ticketCookie.setPath("/");
     * 1.setPath("/") cookie的权限路径
     * url1:http://www.jt.com/a.html
     * url2:http://www.jt.com/user/b.html
     * 这两个页面都能拿到cookie
     *
     * //cookie和域名是绑定的
     * //一个域名只能看到自己的Cookie记录
     * //request对象只能获取cookie的简单记录,k:v Path
     *
     * 4cookie被盗用
     *  1.IP校验 uuid:
     *          user:userJson
     *          ip:真实的userIp地址
     *  现在登录IP ==jedis.hget("UUID","IP")
     *   2短信验证、校验验证码
     *   3人脸识别 2红外3照相 指纹
     *   4探针 酒店 手机号 副卡
     *
     * @param user
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){
        String ticket=userService.findUserByUP(user);//返回uuid
        if(StringUtils.isEmpty(ticket)){
            return SysResult.fail("账户密码为空");
        }
        //
        Cookie ticketCookie= new Cookie("JT_TICKET",ticket);
       ticketCookie.setMaxAge(7*24*60*60);
       //>0有超时时间 单位秒
        //=0要求立即删除cookie
        //-1 关闭会话后（浏览器关闭窗口），删除cookie
//       约定俗成是/,主机的哪些路径可以看到cookie
        //
       ticketCookie.setPath("/");
       ticketCookie.setDomain("jt.com");//在该域名下实现数据的共享，不同主机都可以看到域名www.jt.com的cookie,manager.jt.com也能看到
        response.addCookie(ticketCookie);
        return SysResult.ok();
    }


    /**
     * 业务需求:退出
     * 先查询cookie数据
     * 删除redis上的信息，
     * 删除用户浏览器的cookie
     * 冲定向到系统的首页
     *
     *
     */

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            return "redirect:/";
        }
            String ticket=null;
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("JT_TICKET")){
                    ticket=cookie.getValue();
                    break;
                }
            }
            if(StringUtils.isEmpty(ticket)){
                return "redirect:/";
            }

        //有ticket
        jedisCluster.del(ticket);
            Cookie cookie=new Cookie("JT_TICKET","");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setDomain("jt.com");

            response.addCookie(cookie);

        return "redirect:/";

    }




}
