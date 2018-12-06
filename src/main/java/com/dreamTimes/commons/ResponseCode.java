package com.dreamTimes.commons;

/**
 * 定义状态码
 */
public enum ResponseCode {
    PARAM_EMPTY(2,"参数不能为空"),
    USER_NOT_EXITS(3,"用户不存在"),
    PASSWORD_ERROR(4,"密码错误"),
    USER_EXITS(5,"用户已存在"),
    EMAIL_EXITS(6,"邮箱已存在"),
    REGISTER_FAIL(7,"注册失败"),
    TYPE_ERROR(8,"type参数有误"),
    USER_NOT_LOGIN(9,"用户未登陆"),
    QUESTION_ERROR(10,"该用户未设置密保问题"),
    ANSWER_ERROR(11,"问题答案错误"),
    TOKEN_OUT(12,"token失效"),
    ALTER_PASSWORD_FAIL(13,"修改密码失败"),
    TOKEN_OUT_DATE(14,"无效的token")

    ;
    private Integer status;
    private String msg;

    ResponseCode() {
    }

    ResponseCode(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
