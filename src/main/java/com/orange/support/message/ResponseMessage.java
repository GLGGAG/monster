package com.orange.support.message;

/**
 * @author:GLGGAG
 * @Date:2017/10/18 16:24
 * @Description:响应消息格式，与前端响应消息必须由此类承载
 */
public class ResponseMessage<T> extends Message {

    private static final long serialVersionUID = 1L;

    /**
     * 响应代号
     */
    private String code;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * 消息内容体
     */
    private T data;

    public ResponseMessage() {
        this(ErrorMsg.Success);
    }
    public ResponseMessage(ErrorMsg err) {
        super();
        this.code = err.getCode();
        this.msg = err.getMsg();
    }

    public ResponseMessage(ErrorMsg err, String msg) {
        super();
        this.code = err.getCode();
        this.msg = msg;
    }

    public ResponseMessage(ErrorMsg err, T data){
    	 this.code = err.getCode();
         this.msg = err.getMsg();
         this.data = data;
    }
    
    public ResponseMessage(T data) {
    	this();
        this.data = data;
    }
    public ResponseMessage(String code, String message) {
    	this.code=code;
        this.msg = message;
    }

    public ResponseMessage(String code, String message, T data) {
        this(code, message);
        this.data=data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    /**
     * 处理成功
     *
     * @return
     */
    public boolean succeed() {
        return ErrorMsg.Success.getCode().equals(code);
    }

}