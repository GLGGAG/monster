package com.orange.interceptor;

import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.support.util.JSONUtil;
import com.orange.support.util.LogUtil;
import com.orange.support.util.RequestUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author:GLGGAG
 * @Date:2017/10/19 16:24
 * @Description: 请求绑定日记上下文拦截器
 */
public class LogBindInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        LogUtil.bind(RequestUtils.logContextInfo());
        String url = request.getServletPath();
        if (url.indexOf("login/verify")>0){
            return true;
        }
        LoginUserSession us = (LoginUserSession)request.getSession().getAttribute(GlobalConstant.USER_SESSION_KEY);
        if ( us == null ){
            response.setContentType("text/javascript");
            response.setCharacterEncoding("utf-8");
            ServletOutputStream os = response.getOutputStream();
            os.write(JSONUtil.toJson(new ResponseMessage<Object>(ErrorMsg.UserNotLogin)).getBytes());
            return  false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (ex != null) {
            if (LogUtil.ROOT_LOG.isDebugEnabled()) {
                LogUtil.ROOT_LOG.debug("request was over, but have exception: " + ex.getMessage());
            }
        }

        Map<String, String> logContextMap = MDC.getCopyOfContextMap();
        String logJson = JSONUtil.toJson(logContextMap);
        LogUtil.ROOT_LOG.info(logJson);
        LogUtil.unbind();
    }
}
