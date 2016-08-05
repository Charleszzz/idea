package com.idea.exception;

/**
 * Created by Administrator on 2016/5/21.
 */
public class RepeatException extends SeckillException {
    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}
