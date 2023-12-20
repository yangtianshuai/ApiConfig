package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class DemoApplicationTests {

//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Test
//    void testContextLoads() {
//        assertNotNull(this.applicationContext);
//    }

    @Test
    @DisplayName("hello")
    public void hello () {
        System.out.println("Hello Spring Boot Test");
    }

}
