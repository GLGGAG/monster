package com.orange.support.common;



import com.orange.support.exception.ServiceException;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.support.util.CollectionUtil;
import com.orange.support.util.CommonUtil;
import com.orange.support.util.LogUtil;
import com.orange.support.util.RequestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author:GLGGAG
 * @Date:2017/10/18 16:20
 * @Description:全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Value("${online:false}")
    private boolean online;

    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound(ResourceNotFoundException e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isDebugEnabled()){
            LogUtil.ROOT_LOG.debug(e.getMessage(), e);
        }
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, "未找到资源"), response);
    }

    /** 请求没有相应的处理 */
    @ExceptionHandler(NoHandlerFoundException.class)
    public void forbidden(NoHandlerFoundException e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isDebugEnabled()) {
            LogUtil.ROOT_LOG.debug(e.getMessage(), e);
        }

        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, "无对应的请求"), response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void notSupported(HttpRequestMethodNotSupportedException e,
                             HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isDebugEnabled()) {
            LogUtil.ROOT_LOG.debug(e.getMessage());
        }

        String msg = CommonUtil.EMPTY;
        if (!online) {
            msg = String.format(" 当前方式(%s), 支持方式(%s)", e.getMethod(), CollectionUtil.toStr(e.getSupportedMethods()));
        }
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, "不支持此种请求方式!" + msg), response);
    }

    /** 业务异常 */
    @ExceptionHandler(ServiceException.class)
    public void serviceException(ServiceException e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isDebugEnabled()) {
            LogUtil.ROOT_LOG.debug(e.getMessage());
        }
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, e.getMessage()), response);
    }

    /** 上传文件太大 */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void notFound(MaxUploadSizeExceededException e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isDebugEnabled()) {
            LogUtil.ROOT_LOG.debug("文件太大: " + e.getMessage(), e);
        }
        // 右移 20 位相当于除以两次 1024, 正好表示从字节到 Mb
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, "上传文件太大! 请保持在 " + (e.getMaxUploadSize() >> 20) + "M 以内"), response);
    }

    /** 未知的所有其他异常 */
    @ExceptionHandler(Throwable.class)
    public void exception(Throwable e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isErrorEnabled()) {
            LogUtil.ROOT_LOG.error("有错误: " + e.getMessage(), e);
        }
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, (online ? "请求时出现错误, 我们将会尽快处理." : e.getMessage())), response);
    }

    /** 无效参数异常 */
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(Throwable e, HttpServletResponse response) throws IOException {
        if (LogUtil.ROOT_LOG.isErrorEnabled()) {
            LogUtil.ROOT_LOG.error("有错误: " + e.getMessage(), e);
        }
        RequestUtils.toJson(new ResponseMessage(ErrorMsg.SystemErr, (online ? "请求时出现错误, 我们将会尽快处理." : e.getMessage())), response);
    }

}
