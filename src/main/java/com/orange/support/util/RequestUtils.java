package com.orange.support.util;

import com.orange.support.message.ResponseMessage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author:GLGGAG
 * @Date:2017/10/19 16:24
 * @Description: <span style="color:red;">!!!此工具类请只在 controller 中调用!!!</span>
 */
public final class RequestUtils {

    private static final String USER_AGENT = "user-agent";
    private static final String REFERRER = "referer";

    /**
     * 获取真实客户端IP
     * 关于 X-Forwarded-For 参考: http://zh.wikipedia.org/wiki/X-Forwarded-For<br>
     * 这一 HTTP 头一般格式如下:
     * X-Forwarded-For: client1, proxy1, proxy2,<br><br>
     * 其中的值通过一个 逗号 + 空格 把多个 IP 地址区分开, 最左边(client1)是最原始客户端的IP地址,
     * 代理服务器每成功收到一个请求，就把请求来源IP地址添加到右边
     */
    public static String getRealIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        ip = request.getHeader("X-Forwarded-For");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为 真实 ip
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        ip = request.getHeader("X-Cluster-Client-IP");
        if (CommonUtil.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        return request.getRemoteAddr();
    }

    public static String userAgent() {
        return getRequest().getHeader(USER_AGENT);
    }
    /**
     * 格式化参数, 如果是文件流(form 表单中有 type="multipart/form-data" 这种), 则不打印出参数
     *
     * @return 示例: id=xxx&name=yyy
     */
    public static String formatParam() {
        // return getRequest().getQueryString(); // 没有时将会返回 null
        return CommonUtil.formatParam(getRequest().getParameterMap());
    }

    /** 请求头里的 referer 这个单词拼写是错误的, 应该是 referrer, 然而历史遗留原因导致这个问题永远不会被更正 */
    public static String getReferrer() {
        return getRequest().getHeader(REFERRER);
    }

    /** 先从请求头中查, 为空再从参数中查 */
    public static String getHeaderOrParam(String param) {
        HttpServletRequest request = getRequest();
        String header = request.getHeader(param);
        if (CommonUtil.isBlank(header)) {
            header = request.getParameter(param);
        }
        return CommonUtil.isBlank(header) ? CommonUtil.EMPTY : header.trim();
    }

    /** 格式化头里的参数: 每对值以换行隔开, 键值以冒号分隔 */
    public static String formatHeadParam() {
        HttpServletRequest request = getRequest();

        StringBuilder sbd = new StringBuilder();
        int i = 0;
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            if (i > 0){
                sbd.append("\n");
            }
            String headName = headerNames.nextElement();
            sbd.append(headName).append(" : ").append(request.getHeader(headName));
            i++;
        }
        return sbd.toString();
    }

    /**
     * 基于请求上下文生成一个日志需要的上下文信息对象
     */
    public static LogUtil.LogContextInfo logContextInfo() {
        HttpServletRequest request = getRequest();

        return new LogUtil.LogContextInfo().setIp(getRealIp())
                .setMethod(request.getMethod())
                .setUrl(request.getRequestURL().toString())
                .setParam(formatParam())
                .setHeadParam(formatHeadParam());
    }


    public static <T> void toJson(ResponseMessage<T> result, HttpServletResponse response) throws IOException {
        String json = JSONUtil.toRender(result);
        if (LogUtil.ROOT_LOG.isInfoEnabled()) {
            LogUtil.ROOT_LOG.info("return json: " + json);
        }
        response.setContentType("application/json;charset=UTF-8;");
        response.getWriter().write(json);
    }


    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }
}
