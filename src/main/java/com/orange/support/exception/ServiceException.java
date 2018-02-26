package com.orange.support.exception;

import com.orange.support.message.ErrorMsg;

/**
 * @author:GLGGAG
 * @Date:2017/10/18 16:24
 * @Description:业务异常. 与 ServiceMustHandleException 不同, 此异常会跳到一个指定的错误页面而丢失原先的用户操作
 */
public class ServiceException extends RuntimeException {

    private String code;

    private String desc;

    //构造方法
    public ServiceException() {
        super("业务异常");
        this.code = ErrorMsg.SystemErr.getCode();
        this.desc = "业务异常";
    }

    public ServiceException(String desc) {
        super(desc);
        this.code = ErrorMsg.SystemErr.getCode();
        this.desc = desc;
    }

    public ServiceException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

    public ServiceException(ErrorMsg errorMsg, String desc) {
        super(desc);
        this.code = errorMsg.getCode();
        this.desc = desc;
    }

    public ServiceException(ErrorMsg errorMsg) {
        super(errorMsg.getMsg());
        this.code = errorMsg.getCode();
        this.desc = errorMsg.getMsg();
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
