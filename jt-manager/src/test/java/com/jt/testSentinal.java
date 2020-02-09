package com.jt;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class testSentinal {

    public void testSentinel(){
        //一个集群里面有很多哨兵维持了高可用
      //  https://www.cnblogs.com/chenmh/p/5578376.html
        Set<String>  sentinels=new HashSet<>();

        sentinels.add("47.110.52.14:26379");
        //mymaster是哨兵配置文件里面设置的被监控集群的主机key,
        JedisSentinelPool pool=new JedisSentinelPool("mymaster",sentinels   );
        //从哨兵池中拿一个哨兵（用户拿到这个哨兵（相当于连接其中一台节点，通过它对整个集群进行访问））
        Jedis jedis = pool.getResource();
        jedis.set("1993sentinel","");


    }


}
