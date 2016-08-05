package com.idea.exception;

/**
 * Created by Administrator on 2016/5/21.
 */
public class CloseException extends SeckillException {
    public CloseException(String message) {
        super(message);
    }

    public CloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
