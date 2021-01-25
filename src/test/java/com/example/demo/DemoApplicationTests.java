package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.context.FunctionRegistry;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    FunctionRegistry functionRegistry;

	@Test
	void contextLoads() {
	    assertThat(functionRegistry.getNames(Consumer.class))
                .contains("messageConsumer", "anotherMessageConsumer");
	}

}
