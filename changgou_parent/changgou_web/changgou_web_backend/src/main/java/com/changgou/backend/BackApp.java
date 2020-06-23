package com.changgou.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.changgou.order.feign"})
public class BackApp {
    public static void main(String[] args) {
        SpringApplication.run(BackApp.class,args);
    }
}
