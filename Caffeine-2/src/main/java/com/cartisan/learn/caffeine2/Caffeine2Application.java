package com.cartisan.learn.caffeine2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Caffeine2Application {

    public static void main(String[] args) {
        SpringApplication.run(Caffeine2Application.class, args);
    }

}
