package com.changgou.comment;

import com.changgou.comment.config.TokenDecode;
import com.changgou.interceptor.FeignInterceptor;
import com.changgou.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Program: changgou_parent
 * @ClassName: CommentApplication
 * @Description:
 * @Author: KyleSun
 **/

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableScheduling
@EnableFeignClients(basePackages = {"com.changgou.goods.feign","com.changgou.user.feign"})
@EnableDiscoveryClient
@EntityScan("com.changgou.comment.pojo.comment")
public class CommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
