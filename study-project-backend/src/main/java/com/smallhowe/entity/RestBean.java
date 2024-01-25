package com.smallhowe.entity;

import lombok.Data;

@Data
public class RestBean<T> {
    private int status;
    private boolean success;
    private T msg;
    private T expire;

    public RestBean(int status, boolean success, T msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public RestBean(int status, boolean success, T msg, T expire) {
        this.status = status;
        this.success = success;
        this.msg = msg;
        this.expire = expire;
    }

    public RestBean() {
    }

    public static <T> RestBean<T> success(){
        return new RestBean<>(200,true,null);
    }
    public static <T> RestBean<T> success(T data){
        return new RestBean<>(200,true,data);
    }
    public static <T> RestBean<T> failure(int status, T msg){
        return new RestBean<>(status, false, msg);
    }
    public static <T> RestBean<T> failure(int status, T msg, T expire) {
        return new RestBean<>(status, false, msg, expire);
    }

}
