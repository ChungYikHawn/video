package com.hang.common;

public class SysException extends Exception{
    private String message;
    private Integer code;

    public SysException(String message) {
        this.message = message;
        this.code=500;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
