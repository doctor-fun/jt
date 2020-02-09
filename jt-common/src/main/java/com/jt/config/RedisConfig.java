package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

//    @Value("${jedis.host}")
//    private String host;
//    @Value("${jedis.port}")
//    private  Integer port;

//    @Bean
//    public Jedis jedis(){
//        return  new Jedis(host,port);
//    }

//    @Value("${redis.shards}")
//    private  String redisShards;//node,node,node


    @Value("${redis.nodes}")
    private String redisNodes;

//    List<JedisShardInfo> shards=new ArrayList<>();
//    JedisShardInfo info1=new JedisShardInfo("47.110.52.14","6379");
//    JedisShardInfo info2=new JedisShardInfo("47.110.52.14","6380");
//    JedisShardInfo info3=new JedisShardInfo("47.110.52.14","6381");
//        shards.add(info1);
//        shards.add(info2);
//        shards.add(info3);


//    //往大redis里面放数据
//    ShardedJedis shardedJedis=new ShardedJedis(shards);
//    redis.nodes=47.110.52.14:6379,47.110.52.14:6380,47.110.52.14:6381
//    @Bean
//    @Scope("prototype")//多例
//    public ShardedJedis getShardedJedis(){
//            List<JedisShardInfo> shardsInfoList=new ArrayList<>();
//        //node="ip:端口号"
//        String[]   shards=redisShards.split(",");
//        for (String shard : shards) {
//
//            String host=shard.split(":")[0];
//            int port =Integer.parseInt(shard.split(":")[1]);
//            JedisShardInfo shardInfo=new JedisShardInfo( new HostAndPort(host,port));
//            shardsInfoList.add(shardInfo);
//
//        }
//        return  new ShardedJedis(shardsInfoList);
//
//    }

//
//    public class testSentinal {
//
//        public void testSentinel(){
//            //一个集群里面有很多哨兵维持了高可用
//            //  https://www.cnblogs.com/chenmh/p/5578376.html
//            Set<String>  sentinels=new HashSet<>();
//
//            sentinels.add("47.110.52.14:26379");
//            //mymaster是哨兵配置文件里面设置的被监控集群的主机key,
//            JedisSentinelPool pool=new JedisSentinelPool("mymaster",sentinels   );
//            //从哨兵池中拿一个哨兵（用户拿到这个哨兵（相当于连接其中一台节点，通过它对整个集群进行访问））
//            Jedis jedis = pool.getResource();
//            jedis.set("1993sentinel","");
//
//
//        }
//
//
////    }
//    @Value("${redis.sentinel}")
//    private String redisSentinels;
//
//    /**
//     * 通过哨兵池连接redis小集群，1主两从，主挂了谁上主，是哨兵解决的
//     * @return
//     */
//
//    @Bean
//    public JedisSentinelPool getJedisSentinelPool(){
//        Set<String> sentinels=new HashSet<>();
//        String[]   sentinelsString=redisSentinels.split(",");
//        for (String sentinel : sentinelsString) {
//            sentinels.add(sentinel);
//
//        }
//        JedisSentinelPool pool=new JedisSentinelPool("mymaster",sentinels);
//
//
//        return  pool;
//    }

//    public class SpringTestRedisCluster {
//        @Test
//        public  void testCluster(){
//            Set<HostAndPort> nodes=new HashSet<HostAndPort>();
//            nodes.add(new HostAndPort("47.110.52.14",7000));
//            nodes.add(new HostAndPort("47.110.52.14",7001));
//            nodes.add(new HostAndPort("47.110.52.14",7002));
//            nodes.add(new HostAndPort("47.110.52.14",7003));
//            nodes.add(new HostAndPort("47.110.52.14",7004));
//            nodes.add(new HostAndPort("47.110.52.14",7005));
//            JedisCluster cluster=new JedisCluster(nodes);
//            cluster.set("key","redis集群搭建成功");
//            System.out.println(cluster.get("key"));
//        }
//
//    }
    //通过集群的方式对集群进行管理
@Bean
@Scope("prototype")//多例
public JedisCluster getJedisCluseter(){



    Set<HostAndPort> nodesInfos=new HashSet<HostAndPort>();
    //node="ip:端口号"
    String[]   nodes =redisNodes.split(",");
    for (String node :nodes ) {

        String host=node.split(":")[0];
        int port =Integer.parseInt(node.split(":")[1]);
        nodesInfos.add(new HostAndPort(host,port));


    }
    JedisCluster jedisCluster=new JedisCluster(nodesInfos);
    return  jedisCluster;
}

}
