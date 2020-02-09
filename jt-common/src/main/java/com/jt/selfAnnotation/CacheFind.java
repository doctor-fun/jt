package com.jt.selfAnnotation;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//该注解对方法有效
@Retention(RetentionPolicy.RUNTIME)//表示运行时有效
public @interface CacheFind {
    //注解的values是什么@CacheFind(....)
    //用户可以自己指定，也可以动态生成
        public String key() default "";
        public int Seconds() default 0;//设定超时时间，单位是秒


}
