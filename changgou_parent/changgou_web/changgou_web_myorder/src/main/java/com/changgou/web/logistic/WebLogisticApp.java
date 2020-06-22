package com.changgou.web.logistic;

import com.changgou.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.changgou.logisitic.feign","com.changgou.order.feign"})
public class WebLogisticApp {
    public static void main(String[] args) {
        SpringApplication.run(WebLogisticApp.class,args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }


}
