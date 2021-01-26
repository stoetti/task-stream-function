package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.cloud.stream.kafka.binder.brokers=PLAINTEXT://localhost:19092",
        "spring.cloud.function.definition=messageConsumer;anotherMessageConsumer"
})
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:29092", "port=29092"})
class FailingDemoApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context.containsBeanDefinition("messageConsumer-in-0")).isTrue();
    }

}
