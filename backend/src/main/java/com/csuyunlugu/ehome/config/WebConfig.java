package com.csuyunlugu.ehome.config;

import com.csuyunlugu.ehome.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/5 14:13
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JWTInterceptor jwtInterceptor;

    public WebConfig(JWTInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Value("${jwt.exclude-urls}")
    private String[] excludeUrls;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/wxLogin", "/api/admin/login", "/uploads/**");
    }
}
