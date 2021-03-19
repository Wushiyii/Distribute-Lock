package com.wushiyii.exception;

/**
 * @Author: wgq
 * @Date: 2021/3/18 19:50
 */
public class DistributeLockException extends RuntimeException {

    private Integer code;
    private String message;

    public DistributeLockException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public DistributeLockException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public DistributeLockException(String message) {
        super(message);
        this.message = message;
    }
}