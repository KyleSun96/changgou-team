package com.changgou.page;

import com.changgou.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.awt.print.Pageable;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.goods.feign","com.changgou.comment.feign"})
public class PageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class, args);
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

}
