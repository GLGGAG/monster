package com.orange.support.util;

import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;

import javax.servlet.http.HttpSession;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 11:56
 * @Description:session  帮助类
 */
public class SessionHelper {


    public static LoginUserSession getLoginUserSession(HttpSession session){
        LoginUserSession us = (LoginUserSession)session.getAttribute(GlobalConstant.USER_SESSION_KEY);
        return us;
    }

}
