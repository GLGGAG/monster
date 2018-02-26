package com.orange.support.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:GLGGAG
 * @Date:2017/10/18 16:24
 * @Description:日志管理, 使用此 utils 获取 log, 不要在类中使用 LoggerFactory.getLogger 的方式!
 */
public final class LogUtil {

    /**
     *  根日志: 在类里面使用 LoggerFactory.getLogger(XXX.class) 跟这种方式一样!
     */
    public static final Logger ROOT_LOG = LoggerFactory.getLogger("root");
    /**
     *  SQL 相关的日志
     */
    public static final Logger SQL_LOG = LoggerFactory.getLogger("sqlLog");
    /**
     * 接收到请求的时间. 在 log.xml 中使用 %X{receiveTime} 获取
     */
    private static final String RECEIVE_TIME = "receiveTime";
    /**
     * 请求信息, 包括有 ip、url, param 等
     */
    private static final String REQUEST_INFO = "requestInfo";
    /**
     * 请求里包含的头信息
     */
    private static final String HEAD_INFO = "headInfo";

    /**
     * 输出当前请求信息, 在日志中显示
     * @param logContextInfo 当前请求线程的日记上下文
     */
    public static void bind(LogContextInfo logContextInfo) {
        // 接收到请求的时间, 打印到毫秒级别
        MDC.put(RECEIVE_TIME, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())+"");
        MDC.put(REQUEST_INFO, logContextInfo.paramInfo());
        MDC.put(HEAD_INFO, logContextInfo.headParamInfo());
    }
    public static void unbind() {
        MDC.clear();
    }

    public static class LogContextInfo {
        /**
         * 访问 ip
         */
        String ip;
        /**
         * 访问方法
         */
        String method;
        /**
         * 访问地址
         */
        String url;
        /**
         * 请求 body 中的参数
         */
        String param;
        /**
         * 请求 header 中的参数
         */
        String headParam;

        /**
         * 功能:格式化参数信息
         * @return 字符串形式的参数信息
         */
        private String paramInfo() {
            return String.format("%s (%s %s) param(%s)", ip, method, url, param);
        }

        /**
         * 功能:格式化请求头参数信息
         * @return 字符串形式的请求头参数信息
         */
        private String headParamInfo() {
            return String.format("header(%s)", headParam);
        }

        public String getIp() {
            return ip;
        }

        public LogContextInfo setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public String getMethod() {
            return method;
        }
        public LogContextInfo setMethod(String method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }
        public LogContextInfo setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getParam() {
            return param;
        }
        public LogContextInfo setParam(String param) {
            this.param = param;
            return this;
        }

        public String getHeadParam() {
            return headParam;
        }
        public LogContextInfo setHeadParam(String headParam) {
            this.headParam = headParam;
            return this;
        }
    }
}
