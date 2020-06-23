package com.changgou;

import com.changgou.user.config.TokenDecode;
import com.changgou.user.util.FastDFSClient;
import com.changgou.user.util.FastDFSFile;
import com.changgou.user.util.OSSClientUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.user.dao"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    @Bean
    public OSSClientUtil ossClientUtil() {
        return new OSSClientUtil();
    }

    @Bean
    public FastDFSFile fastDFSFile() {
        return new FastDFSFile();
    }

    @Bean
    public FastDFSClient fastDFSClient() {
        return new FastDFSClient();
    }


}
