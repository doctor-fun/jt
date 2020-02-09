package com.jt.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
//    /**
//     * 将多台redis当做1台使用
//     */
//    @SpringBootTest
//    public class TestRedisShards {
//        @Test
//        public void testShards(){
//            List<JedisShardInfo> shards=new ArrayList<>();
//            JedisShardInfo info1=new JedisShardInfo("47.110.52.14","6379");
//            JedisShardInfo info2=new JedisShardInfo("47.110.52.14","6380");
//            JedisShardInfo info3=new JedisShardInfo("47.110.52.14","6381");
//            shards.add(info1);
//            shards.add(info2);
//            shards.add(info3);
//
//
//            //往大redis里面放数据
//            ShardedJedis shardedJedis=new ShardedJedis(shards);
//            shardedJedis.set("1902班","1902班");
//            System.out.println(shardedJedis.get("1902班"));  ;
//
//        }
//    }
}
