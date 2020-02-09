package com.jt;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class testJedis {
    private Jedis jedis;
    /**
     * redis入门
     * Host：redis的IP地址
     * port:redis的端口号
     */
    @Test
    public void testString()    {
        Jedis jedis =new Jedis("47.110.52.14",6379);
        jedis.set("name","小鸡");
        String value1= jedis.get("name");
        System.out.println(value1);
//set会覆盖之前的数据
        jedis.set("name","小鸡吃寄吧");
    String value2=    jedis.get("name");
        System.out.println(value2);


    }


    @Test
    public void testString2(){
        jedis=new Jedis("47.110.52.14",6379);
        //setnx如果Key相同，则不允许key重复赋值
        jedis.setnx("1909","今天周三");
        jedis.setnx("1909","今天周四");
        System.out.println(jedis.get("1909"));
    }

    @Test
    public void testString3() throws InterruptedException {
        jedis=new Jedis("47.110.52.14",6379);
        //setnx如果Key相同，则不允许赋值
        jedis.set("eat","今天周三");
        jedis.expire("eat",10);
        Thread.sleep(2000);
        //看看还剩余多少生命周期
        System.out.println(jedis.ttl("eat"));
        //超时时间并且同时完成赋值操作（保证两个操作的原子性）
        jedis.setex("eat",10,"今天周四");
    }

    /**
     *   params:
     * 	 	XX:允许覆盖
     *   			NX:不允许覆盖
     *   			PX:毫秒
     *  			EX:秒
     *
     * 1保证数据不被修改
     * 2.同时保证赋值操作与添加超时时间的操作是原子性的
     */
    @Test
    public void testString4(){
        SetParams setParams=new SetParams();
        setParams.ex(10).nx();
        jedis=new Jedis("47.110.52.14",6379);
        jedis.set("1909","您好",setParams);
    }


    @Before
    public  void init(){
        jedis=new Jedis("47.110.52.14",6379);
    }
    @Test
    //hash一般比较适合存对象
    public void  testHash(){
        Jedis jedis=new Jedis("47.110.52.14",6379);
        jedis.hset("person","id","200");
        jedis.hset("person","name","tomcat");

        Map<String,String > map =new HashMap<>();
        map.put("id","2000");
        map.put("name","111");
        map.put("age","180");
        jedis.hset("stu",map);
        System.out.println(jedis.hvals("person"));
        System.out.println(jedis.hgetAll("stu"));

    }

}
