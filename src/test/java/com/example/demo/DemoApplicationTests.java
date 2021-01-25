package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.task.listener.TaskEventAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.cloud.stream.kafka.binder.brokers=PLAINTEXT://localhost:19092"
})
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=29092"})
@EnableAutoConfiguration(exclude = TaskEventAutoConfiguration.class)
class DemoApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context.containsBeanDefinition("messageConsumer-in-0")).isTrue();
    }

}
