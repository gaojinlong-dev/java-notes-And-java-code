package com.atguigu.mq.stream;

import com.rabbitmq.stream.Consumer;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;

import java.util.concurrent.CountDownLatch;

public class TestConsumerOffset {

    public static void main(String[] args) throws InterruptedException {
        Environment environment = Environment.builder()
                .host("192.168.47.100")
                .port(33333)
                .username("atguigu")
                .password("123456")
                .build();

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Consumer consumer = environment.consumerBuilder()
                .stream("stream.atguigu.test")
                .offset(OffsetSpecification.first())
                .messageHandler((offset, message) -> {
                    byte[] bodyAsBinary = message.getBodyAsBinary();
                    String messageContent = new String(bodyAsBinary);
                    System.out.println("[消费者端]messageContent = " + messageContent);
                    countDownLatch.countDown();
                })
                .build();

        countDownLatch.await();

        consumer.close();
    }

}
