package com.csuyunlugu.ehome;

import org.springframework.boot.SpringApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName EHomeApplication
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:32
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.csuyunlugu.ehome.dao")
public class EHomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EHomeApplication.class, args);
    }
}
