package com.idea.dto;

/**
 * Created by zhangyong on 16/7/11.
 */
public class SeckillResult<T> {

    private boolean success;

    private T t;

    private String error;

    public SeckillResult(boolean success, T t) {
        this.success = success;
        this.t = t;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
