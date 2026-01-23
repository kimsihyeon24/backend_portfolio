package com.example.demo;

import com.example.demo.helloboot.SimpleHelloService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloServiceTest {
    @Test
    void simpleHelloService() {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

        String res = simpleHelloService.sayHello("World!");

        Assertions.assertThat(res).isEqualTo("Hello World!");
    }
}
