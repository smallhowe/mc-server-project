package com.smallhowe.entity;

import lombok.Data;

import java.util.Map;

@Data
public class RestBean<T> {
    private int status;
    private boolean success;
    private T msg;
    private Object data;

    public RestBean(int status, boolean success, T msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public RestBean(int status, boolean success, T msg, T data) {
        this.status = status;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }


    public RestBean() {
    }

    public static <T> RestBean<T> success(){
        return new RestBean<>(200,true,null);
    }
    public static <T> RestBean<T> success(T msg){
        return new RestBean<>(200,true,msg);
    }
    public static <T> RestBean<Object> success(T msg,Object data){
        return new RestBean<>(200,true,msg,data);
    }
    public static <T> RestBean<T> failure(int status, T msg){
        return new RestBean<>(status, false, msg);
    }
    public static <T> RestBean<T> failure(int status, T msg, T data) {
        return new RestBean<>(status, false, msg, data);
    }

    public static RestBean<Object> failure(int status, String msg, Map<String, Object> data) {
        return new RestBean<>(status,false, msg, data);
    }
}
