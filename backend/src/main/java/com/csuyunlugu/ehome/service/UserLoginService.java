package com.csuyunlugu.ehome.service;

import com.csuyunlugu.ehome.exception.LoginException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @ClassName UserLoginService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/5 16:24
 * @Version 1.0
 */
@Service
public class UserLoginService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${com.csuyunlugu.wx.appid}")
    private String appId;
    @Value("${com.csuyunlugu.wx.secret}")
    private String appSecret;

    public UserLoginService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getOpenIdByCode(@NotNull @NotBlank String code) {
        try {
            // 调用微信 API 获取 openid
            String tokenUrl = UriComponentsBuilder
                    .fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session")
                    .queryParam("appid", appId)
                    .queryParam("secret", appSecret)
                    .queryParam("js_code", code)
                    .queryParam("grant_type", "authorization_code")
                    .toUriString();
            // 请求微信后台接口 如果 code 无效，会返回错误信息
            String tokenResponse = restTemplate.getForObject(tokenUrl, String.class);
            System.out.println(tokenResponse);
            // 解析返回结果
            JsonNode tokenInfo = objectMapper.readTree(tokenResponse);
            // 错误码不为 0 则表示请求失败
            if (tokenInfo.has("errcode")) {
                int errcode = tokenInfo.get("errcode").asInt();
                String errmsg = tokenInfo.get("errmsg").asText();
                throw LoginException.wxLoginFailed(errcode, errmsg);
            }
            return tokenInfo.get("openid").asText();
        } catch (LoginException e) {
            throw e;
        } catch (Exception e) {
            throw LoginException.wxLoginFailed(500, e.getMessage());
        }
    }
}
