package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.function.context.FunctionRegistry;
import org.springframework.cloud.task.listener.TaskEventAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@Testcontainers
@ContextConfiguration(initializers = DemoApplicationTests.AppInitializer.class)
@EnableAutoConfiguration(exclude = TaskEventAutoConfiguration.class)
class DemoApplicationTests {

    static class AppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of("spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers())
                    .applyTo(applicationContext.getEnvironment());
            TestPropertyValues.of("spring.cloud.stream.kafka.binder.brokers=" + kafka.getBootstrapServers())
                    .applyTo(applicationContext.getEnvironment());
        }

    }

    @Autowired
    FunctionRegistry functionRegistry;

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        assertThat(context.containsBeanDefinition("messageConsumer-in-0")).isTrue();
    }

}
