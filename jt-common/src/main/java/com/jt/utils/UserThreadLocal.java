package com.jt.utils;

import com.jt.pojo.User;

public class UserThreadLocal {
    private static ThreadLocal<User> threadLocal=new ThreadLocal<>();


    public static  void SetUser(User user){
        threadLocal.set(user);

    }

    public static  User getUser(){
        return threadLocal.get();

    }
//本线程可能会生成其他线程执行，一致存在，
    public static void remove(){
        threadLocal.remove();//清空threadlocal
    }
 }

