package com.atguigu.helloredis.test;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * ClassName: TestStringRedisTemplate
 * Package: com.atguigu.helloredis.test
 * Description:
 *
 * @Author 高金龙
 * @Create 2024/10/19 16:26
 * @Version 1.0
 */

@SpringBootTest
public class TestStringRedisTemplate {

    @Autowired(required=false)
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSet() {
        redisTemplate.opsForValue().set("key4", "value4");
    }

    @Test
    public void testGet() {
        String result = redisTemplate.opsForValue().get("key4");
        System.out.println(result);
    }

    @Test
    public void testDel() {
        redisTemplate.delete("key4");
    }
}