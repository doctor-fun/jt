package com.jt.aop;

import com.jt.pojo.ItemDesc;
import com.jt.selfAnnotation.CacheFind;
import com.jt.utils.ObejctMapperUtils;
import com.jt.vo.EasyUITree;
import com.sun.xml.internal.rngom.digested.DValuePattern;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.Method;

@Component
@Aspect
public class CacheAop {
    @Autowired(required =false)
    private JedisCluster jedisCluster;
    //拿到jedisCluste控制器，会拿到其中一个连接

    /**
     * 是否需要获得注解中的参数
     * 需要：切入点表达式写注解名称
     * 不需要：如果只是做表示，使用包名，注解名
     * @param joinPoint
     * @return
     */
    @Around("@annotation(cacheFind)")
    //将方法的注解直接注入到aroud方法的cacheFind里面
    public Object around(ProceedingJoinPoint joinPoint ,CacheFind cacheFind){

        String key=getKey(joinPoint,cacheFind);

        //获取缓存数据
        String result= jedisCluster.get(key);
        Object obj=null;
        if(StringUtils.isEmpty(result)){
            System.out.println("AOP查询数据库");

            //说明用户第一次查询，执行目标方法
            try {
                 obj     = joinPoint.proceed();
                //将数据保存到redis中
                String json= ObejctMapperUtils.toJson(obj);
                if(cacheFind.Seconds()>0){
                    jedisCluster.setex(key,cacheFind.Seconds(),json);
                }else {
                    jedisCluster.set(key,json);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException(throwable);
            }

        }else{
            //缓存有值
            //result为json数据，需要转化为对象
            //目标方法返回值类型
            Class returnType=getReturnType(joinPoint);

            obj = ObejctMapperUtils.toObject(result, returnType);
            System.out.println("aop查询缓存");

        }


        return obj;
    }


    /**
     * 如果用户自己制定了key，则使用用户的，如果没有指定则动态生成
     * @param joinPoint
     * @return
     */
    private  String getKey(ProceedingJoinPoint joinPoint,CacheFind cacheFind){
        String key=cacheFind.key();
        //后去用户自己的参数
        if(!StringUtils.isEmpty(key)){
            return  key;
            
        }
        //如果用户没有传递，则动态生成
        Signature signature = joinPoint.getSignature();//控制方法的而生成的管理类对象，提供控制api
        String declaringTypeName = signature.getDeclaringTypeName();//全限定类名
        String name = signature.getName();//得到方法名
       Long args0 = (Long)joinPoint.getArgs()[0];//获得目标方法的参数列表
        key=declaringTypeName +name+"::"+args0;//注解cacheFind的某个方法的cacha的key名，
        return  key;
    }


    /**
     * 获得方法返回值
     * @param joinPoint
     * @return
     */
    private Class<?> getReturnType(ProceedingJoinPoint joinPoint) {
        //MethodSignature方法签名 MethodSignature是signature的继承后代
        MethodSignature signature =(MethodSignature)joinPoint.getSignature();

        return signature.getReturnType();
    }
}
