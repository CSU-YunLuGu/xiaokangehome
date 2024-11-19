package com.csuyunlugu.ehome.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName JwtUtil
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 20:56
 * @Version 1.0
 */
@Component
public class JWTUtil {
    // 密钥
    private static String SECRET_KEY;
    // 过期时间
    private static long EXPIRATION_TIME;

    public JWTUtil(
            @Value("${com.csuyunlugu.jwt.secret}") String secretKey,
            @Value("${com.csuyunlugu.jwt.expiration}") long expirationTime) {
        JWTUtil.SECRET_KEY = secretKey;
        JWTUtil.EXPIRATION_TIME = expirationTime;
    }

    public static String generateToken(String openid) {
        // 获取 openid 作为 subject 并设置过期时间 封装成 token
        // header = {"alg": "HMAC512", "typ": "JWT"}
        // payload = {"sub": "${openid}", "exp": 86400000}
        // signature = alg(base64(header).base64(payload), secret_key)
        // token = base64(header).base64(payload).base64(signature)
        return JWT.create()
                .withSubject(openid)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public static DecodedJWT verifyToken(String token) {
        // 验证 token 有效性
        try {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
        } catch (JWTVerificationException e) {
            throw e;
        }
    }

    public static String getSubjectFromToken(String token) {
        // 先验证 token 有效性，再获取 subject 即 openid
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public static boolean isTokenExpired(String token) {
        // 验证 token 有效性，并验证是否过期
        DecodedJWT decodedJWT = verifyToken(token);
        if (decodedJWT != null) {
            Date expiration = decodedJWT.getExpiresAt();
            // 过期时间小于当前时间，则过期
            return expiration.before(new Date());
        }
        // 其他情况，token 有效
        return false;
    }
}
