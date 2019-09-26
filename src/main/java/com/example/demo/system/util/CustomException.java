package com.example.demo.system.util;

/**
 * Description: 自定义异常
 *
 * @date 2019年09月26日 16:23
 * Version 1.0
 */
public class CustomException extends Exception {
    private static final long	serialVersionUID	= 12L;
    private int					errorCode;
    private String				message;

    public CustomException(int errorCode){
        this.errorCode = errorCode;
    }

    public CustomException(int errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public CustomException(String message){
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
