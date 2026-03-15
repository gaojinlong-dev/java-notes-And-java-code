package com.atguigu.helloredis.test;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * ClassName: TestRedisTemplate
 * Package: com.atguigu.helloredis.test
 * Description:
 *
 * @Author 高金龙
 * @Create 2024/10/19 15:13
 * @Version 1.0
 */
@SpringBootTest
public class TestRedisTemplate {
//    @Resource
    @Autowired(required=false)
    private RedisTemplate redisTemplate;

    @Test
    public void testSet(){
        redisTemplate.opsForValue().set("key1", "value1");

    }

    @Test
    public void testGet(){
        String result = (String) redisTemplate.opsForValue().get("key1");
        System.out.println(result);

    }

    @Test
    public void testDel(){
        redisTemplate.delete("key1");

    }
}
