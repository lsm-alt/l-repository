package com.codedata.rbac.common.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public ServiceException(int code) {
        super(code + "");
        this.code = code;
        this.msg = ErrorCode.get(code);
    }

    public ServiceException(int code, Throwable e) {
        super(code + "", e);
        this.code = code;
        this.msg = ErrorCode.get(code);
    }

    public ServiceException(int code, String msg) {
        super(code + "-" + msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(int code, String msg, Throwable e) {
        super(code + "-" + msg, e);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        super(msg);
        this.code = ErrorCode.ERR_UNKNOWN;
        this.msg = msg;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.ERR_UNKNOWN;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
