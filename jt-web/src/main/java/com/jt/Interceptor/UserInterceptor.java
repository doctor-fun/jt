package com.jt.Interceptor;

import com.jt.pojo.User;
import com.jt.utils.ObejctMapperUtils;
import com.jt.utils.UserThreadLocal;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* @author fangchen
* @date 2020/2/8 3:00 下午
*/
@Component
public class UserInterceptor implements HandlerInterceptor {
    //拦截用户是否登录
    //

    /**
     * 请求放行
     * ：true:请求放行
     *  false:请求拦截 需要配合重定向联用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    /**
     * 在使用controller之前进行拦截
     *
     * 业务
     *  1从cookie中动态获取JT_TICKET数据值
     *  2.判断数据值是否有效
     *  3放行请求
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies=request.getCookies();
        if(cookies==null ||cookies.length==0){
            //重定向到用户的登录页面
            response.sendRedirect("/user/login.html");
            return false;
        }
        String ticket=null;
        for (Cookie cookie : cookies) {
            if("JT_TICKET".equals(cookie.getName())){

                ticket =cookie.getValue();
                break;
            }
        }
        if(StringUtils.isEmpty(ticket)){
            //重定向到用户的登录页面
            response.sendRedirect("/user/login.html");
            return false;
        }
        //ticket用户有值

        String userJson = jedisCluster.get(ticket);

        if(StringUtils.isEmpty(userJson)){
            Cookie ticketCookie = new Cookie("JT_TICKET", "");
            ticketCookie.setDomain("jt.com");
            ticketCookie.setMaxAge(0);
            ticketCookie.setPath("/");
            response.addCookie(ticketCookie);

            //重定向到用户的登录页面
            response.sendRedirect("/user/login.html");
            return false;

        }
        User user = ObejctMapperUtils.toObject(userJson, User.class);
       //这种方式不好，要取域对象model，最好用ThreadLoacl,这样请求线程的任何位置都可以
        //拿到这个变量
        // request.setAttribute("JT_USER",user);//往request域传入
        UserThreadLocal.SetUser(user);
        return true;//请求放行


    }

    /**
     * after是在 渲染前的操作
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //防止内存泄漏 提前清空数据

        UserThreadLocal.remove();
    }

}
