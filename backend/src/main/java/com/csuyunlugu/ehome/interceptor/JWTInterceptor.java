package com.csuyunlugu.ehome.interceptor;

import com.csuyunlugu.ehome.exception.LoginAuthException;
import com.csuyunlugu.ehome.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName JWTInterceptor
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/5 14:04
 * @Version 1.0
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {
    // 拦截除了登录之外的所有请求，检查 token 是否有效
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 authorization header
        String authorization = request.getHeader("Authorization");
        // 校验 token
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw LoginAuthException.unauthorized();
        }
        String token = authorization.substring(7);
        try {
            // 检查 token 是否失效或过期
            if (JWTUtil.isTokenExpired(token)) {
                throw new RuntimeException();
            }
            // 获取 openid
            String openid = JWTUtil.getSubjectFromToken(token);
            request.setAttribute("subject", openid);
            return true;
        } catch (Exception e) {
            throw LoginAuthException.unauthorized();
        }
    }
}
