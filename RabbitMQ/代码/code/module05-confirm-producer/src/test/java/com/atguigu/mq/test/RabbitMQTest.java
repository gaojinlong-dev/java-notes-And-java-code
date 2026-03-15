package com.atguigu.mq.test;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class RabbitMQTest {

    public static final String EXCHANGE_DIRECT = "exchange.direct.order";
    public static final String EXCHANGE_TIMEOUT = "exchange.test.timeout";
    public static final String ROUTING_KEY = "order";
    public static final String ROUTING_KEY_TIMEOUT = "routing.key.test.timeout";
    public static final String EXCHANGE_NORMAL = "exchange.normal.video";
    public static final String ROUTING_KEY_NORMAL = "routing.key.normal.video";
    public static final String EXCHANGE_DELAY = "exchange.test.delay";
    public static final String ROUTING_KEY_DELAY = "routing.key.test.delay";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test01SendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY + "~", "Message Test Confirm~~~ ~~~");
    }

    @Test
    public void test02SendMessage() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(EXCHANGE_DIRECT, ROUTING_KEY, "Test Prefetch " + i);
        }
    }

    @Test
    public void test03SendMessage() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(EXCHANGE_TIMEOUT, ROUTING_KEY_TIMEOUT, "Test timeout " + i);
        }
    }

    @Test
    public void test04SendMessage() {

        // 创建消息后置处理器对象
        MessagePostProcessor postProcessor = message -> {

            // 设置消息的过期时间，单位是毫秒
            message.getMessageProperties().setExpiration("7000");

            return message;
        };

        rabbitTemplate.convertAndSend(EXCHANGE_TIMEOUT, ROUTING_KEY_TIMEOUT, "Test timeout", postProcessor);
    }

    @Test
    public void testSendMultiMessage() {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend(
                    EXCHANGE_NORMAL,
                    ROUTING_KEY_NORMAL,
                    "测试死信情况2：消息数量超过队列的最大容量" + i);
        }
    }

    @Test
    public void test05SendMessageDelay() {

        // 创建消息后置处理器对象
        MessagePostProcessor postProcessor = message -> {

            // 设置消息过期时间（以毫秒为单位）
            // x-delay 参数必须基于 x-delayed-message-exchange 插件才能生效
            message.getMessageProperties().setHeader("x-delay", "10000");

            return message;
        };

        // 发送消息
        rabbitTemplate.convertAndSend(
                EXCHANGE_DELAY,
                ROUTING_KEY_DELAY,
                "Test delay message by plugin " + new SimpleDateFormat("HH:mm:ss").format(new Date()),
                postProcessor);
    }

    public static final String EXCHANGE_PRIORITY = "exchange.test.priority";
    public static final String ROUTING_KEY_PRIORITY = "routing.key.test.priority";

    @Test
    public void test06SendMessage() {
        rabbitTemplate.convertAndSend(EXCHANGE_PRIORITY, ROUTING_KEY_PRIORITY, "message test proirity 3", message -> {

            // 消息本身的优先级数值
            // 切记：不能超过 x-max-priority:	10
            message.getMessageProperties().setPriority(3);

            return message;
        });
    }
}
