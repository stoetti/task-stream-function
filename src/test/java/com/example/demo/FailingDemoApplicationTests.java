package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.function.context.FunctionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@Testcontainers
@ContextConfiguration(initializers = FailingDemoApplicationTests.AppInitializer.class)
class FailingDemoApplicationTests {

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
