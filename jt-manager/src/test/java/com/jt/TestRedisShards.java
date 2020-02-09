package com.jt;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 将多台redis当做1台使用
 */
@SpringBootTest
public class TestRedisShards {
    @Test
    public void testShards(){
        List<JedisShardInfo> shards=new ArrayList<>();
        JedisShardInfo info1=new JedisShardInfo("47.110.52.14","6379");
        JedisShardInfo info2=new JedisShardInfo("47.110.52.14","6380");
        JedisShardInfo info3=new JedisShardInfo("47.110.52.14","6381");
        shards.add(info1);
        shards.add(info2);
        shards.add(info3);


        //往大redis里面放数据
        ShardedJedis shardedJedis=new ShardedJedis(shards);
        shardedJedis.set("1902班","1902班");
        System.out.println(shardedJedis.get("1902班"));  ;

    }
}
