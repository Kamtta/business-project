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
    DELETE_CART_FAIL(30,"删除购物车商品失败"),
    INSERT_ADDRESS_FAIL(31,"新增地址失败"),
    DELETE_ADDRESS_FAIL(32,"删除地址失败"),
    UPDATE_ADDRESS_FAIL(33,"更新地址失败"),
    SELECT_ADDRESS_FAIL(34,"查询地址失败"),
    NOT_SUCH_PRODUCT(35,"没有此商品"),
    CART_EMPTY(36,"购物车为空"),
    STOCK_NOT_ENOUGH(37,"库存不足"),
    PLEASE_ADD_ADDRESS(38,"请添加收货地址"),
    INSERT_ORDER_FAIL(39,"添加订单失败"),
    INSERT_ORDERITEM_FAIL(40,"添加订单明细失败"),
    DELETE_FAIL(41,"删除失败"),
    ORDER_EMPTY(42,"订单为空"),
    ORDER_CANCEL_CANNOT(43,"此订单无法被取消"),
    ORDER_CANCEL_FAIL(44,"该用户没有此订单"),
    NOT_FOUND_ORDER(45,"没有此订单"),
    SENT_ERROR(46,"发货失败"),
    PAY_FAIL(47,"支付宝生成订单失败"),
    EXCEPTION(48,"出现异常,查看服务端异常日记信息")

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


    public enum OrderStatus{
        //        0-已取消  10-未付款  20-已付款  30-已发货  40-已完成  50-已关闭
        ONLINE_PAY(1,"线上支付"),
        ORDER_CANCEL(0,"已取消"),
        NOT_PAYMENT(10,"未付款"),
        FINISH_PAYMENT(20,"已付款"),
        SENT_PRODUCT(30,"已发货"),
        ORDER_FINISHED(40,"已完成"),
        ORDER_CLOSED(50,"已关闭")

        ;
        private Integer code;
        private String desc;

        OrderStatus(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static OrderStatus codeOf(Integer code){
            for (OrderStatus orderStatus:
                 values()) {
                if(code == orderStatus.getCode()){
                    return orderStatus;
                }
            }
            return null;
        }
    }


    public enum PaymentWayEnum{
        //       1-支付宝   2-微信
        ALIPAY(1,"支付宝"),
        WECHAT(2,"微信")

        ;
        private Integer code;
        private String desc;

        PaymentWayEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static PaymentWayEnum codeOf(Integer code){
            for (PaymentWayEnum paymentWayEnum:
                    values()) {
                if(code == paymentWayEnum.getCode()){
                    return paymentWayEnum;
                }
            }
            return null;
        }
    }
}
