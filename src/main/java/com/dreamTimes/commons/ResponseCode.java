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
    TOKEN_FAIL(12,"不能修改其他人的密码"),
    ALTER_PASSWORD_FAIL(13,"修改密码失败"),
    TOKEN_OUT_DATE(14,"无效的token或者token失效"),
    PASSWORDOLD_ERROR(15,"旧密码输入错误"),
    SERVER_ERROR(16,"服务端异常"),
    ROLE_ERROR(17,"没有权限进行访问"),
    CATEGORY_EXITS(18,"类别明已存在"),
    INSERT_CATEGORY_FAIL(19,"添加类别失败"),
    CATEGORY_NOT_FOUND(20,"未找到该品类"),
    CATEGORY_UPDATE_FAIL(21,"更新品类名字失败"),
    INSERT_PRODUCT_FAIL(22,"新增产品失败"),
    UPDATE_PRODUCT_FAIL(23,"更新产品失败"),
    NOT_FOUND_PRODUCT(24,"查询商品失败"),
    UPLOAD_PIC_FAIL(25,"上传图片失败"),
    LIMIT_NUM_SUCCESS(1,"LIMIT_NUM_SUCCESS"),
    LIMIT_NUM_FAIL(0,"LIMIT_NUM_FAIL"),
    INSERT_CART_FAIL(28,"加入购物车失败"),
    UPDATE_FAIL(29,"更新失败"),
    DELETE_CART_FAIL(30,"删除购物车商品失败")

    ;
    private Integer status;
    private String msg;

    ResponseCode() {}

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
