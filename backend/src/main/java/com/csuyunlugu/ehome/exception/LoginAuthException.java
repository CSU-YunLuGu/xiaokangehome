package com.csuyunlugu.ehome.exception;

/**
 * @ClassName LoginAuthException
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/4 21:19
 * @Version 1.0
 */
public class LoginAuthException extends RuntimeException {
    public Integer code;
    public String msg;

    public LoginAuthException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public static LoginAuthException unauthorized() {
        return new LoginAuthException(401, "用户未登录或登录已过期");
    }
}
