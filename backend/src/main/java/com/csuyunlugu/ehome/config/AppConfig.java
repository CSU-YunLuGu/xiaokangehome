package com.csuyunlugu.ehome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 21:16
 * @Version 1.0
 */
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
