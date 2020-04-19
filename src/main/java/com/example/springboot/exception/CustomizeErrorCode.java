package com.example.springboot.exception;

/**
 * 自定义枚举类
 */
public enum CustomizeErrorCode {
    EMAIL_IS_EXIST(201,"邮箱已经注册过了"),
    EMAIL_ILLEGAL(202,"邮箱不合法"),
    EMAIL_OR_PWD_BLANK(203,"邮箱或密码不能为空"),
    INVALID_ADDRESSES(204,"邮箱不正确，无效的地址！"),
    VERIFY_IS_ERROR(205,"验证码错误"),
    REGISTER_FAIL(206,"注册失败"),
    EMAIL_OR_PWD_ERROR(206,"邮箱号或密码错误，登录失败");


    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

}
