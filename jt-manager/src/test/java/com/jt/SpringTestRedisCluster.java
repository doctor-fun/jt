package com.jt;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class SpringTestRedisCluster {
    @Test
            public  void testCluster(){
        Set<HostAndPort> nodes=new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("47.110.52.14",7000));
        nodes.add(new HostAndPort("47.110.52.14",7001));
        nodes.add(new HostAndPort("47.110.52.14",7002));
        nodes.add(new HostAndPort("47.110.52.14",7003));
        nodes.add(new HostAndPort("47.110.52.14",7004));
        nodes.add(new HostAndPort("47.110.52.14",7005));
        JedisCluster cluster=new JedisCluster(nodes);
        cluster.set("key","redis集群搭建成功");
        System.out.println(cluster.get("key"));
    }

}
