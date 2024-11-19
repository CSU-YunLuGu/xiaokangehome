package com.csuyunlugu.ehome.exception;

/**
 * @ClassName LoginException
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/5 16:48
 * @Version 1.0
 */
public class LoginException extends RuntimeException {
    public Integer code;
    public String msg;

    public LoginException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public static LoginException wxLoginFailed(Integer code, String message) {
        return new LoginException(code, "微信登录失败: " +message);
    }

    public static LoginException loginFailed(Integer code, String message) {
        return new LoginException(code, "登录失败: " +message);
    }
}
