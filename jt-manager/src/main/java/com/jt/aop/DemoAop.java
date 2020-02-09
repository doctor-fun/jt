package com.jt.aop;

import com.jt.vo.SysResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
@Aspect
public class DemoAop {

    //相当于方法if符合切入点的条件
    @Pointcut("execution(* com.jt.service..*.*(..))")
    public  void pointCut() {
        System.out.println("所有方");

    }
    @Before("pointCut()")
    public void before(){
        System.out.println("我是一个Before通知");
    }
    //AfterReturning要负责传递目标方法的返回值,一般用于对目标方法进行状态监控,returning就是上
    @AfterReturning(pointcut = "pointCut()",returning = "obj")
    public void afterReturn(Object obj){
        System.out.println(obj);
        System.out.println("目标方法执行之后");
        return;
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object obj=new Object();
        try{
            Long startTime= System.currentTimeMillis();
             obj = joinPoint.proceed();//只支持环绕通知
            Long endTime=System.currentTimeMillis();
            System.out.println("方法执行时间:"+(endTime-startTime));
        }catch (Throwable e){
            e.printStackTrace();
        }
        return obj;//往调用者返回目标方法的返回值
    }

}
