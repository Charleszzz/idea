package com.idea.exception;

/**
 * Created by Administrator on 2016/5/21.
 */
public class DataReWriteException extends SeckillException {
    public DataReWriteException(String message) {
        super(message);
    }

    public DataReWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
