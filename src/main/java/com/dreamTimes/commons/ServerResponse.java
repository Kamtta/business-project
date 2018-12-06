package com.dreamTimes.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 前端高复用类
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {
//    返回的状态
    private int status;
//    返回的数据，数据为json对象
    private T data;
//    返回相关的信息，错误提醒等
    private String msg;

    private ServerResponse(){}
    private ServerResponse(int status){
        this.status = status;
    }
    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status,String msg,T data){
        this.status =status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断接口是否调用成功
     * @return
     */
    @JsonIgnore
    public boolean isSucess(){
        return this.status == Const.SUCCESS_CODE;
    }

    /**
     * 接口调用成功
     * @return
     */
    public static ServerResponse createServerResponseBySuccess(){
        return new ServerResponse(Const.SUCCESS_CODE);
    }

    public static ServerResponse createServerResponseBySuccess(String msg){
        return new ServerResponse(Const.SUCCESS_CODE,msg);
    }

    public static <T> ServerResponse createServerResponseBySuccess(String msg,T data){
        return new ServerResponse(Const.SUCCESS_CODE,msg,data);
    }

    /**
     * 接口调用失败
     * @return
     */
    public static ServerResponse createServerResponseByError(){
        return new ServerResponse(Const.ERROR_CODE);
    }

    public static ServerResponse createServerResponseByError(int status){
        return new ServerResponse(status);
    }


    public static ServerResponse createServerResponseByError(String msg){
        return new ServerResponse(Const.ERROR_CODE,msg);
    }


    public static ServerResponse createServerResponseByError(int status,String msg){
        return new ServerResponse(status,msg);
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
