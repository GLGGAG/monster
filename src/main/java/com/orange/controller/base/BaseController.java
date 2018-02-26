package com.orange.controller.base;

import com.orange.support.common.LoginUserSession;
import com.orange.support.util.SessionHelper;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * @author:GLGGAG
 * @Date:2017/10/19
 * @Description:控制器基类，所有控制器应继承此类
 */
@Controller
public class BaseController {


    public LoginUserSession userSession(HttpSession session){
        return SessionHelper.getLoginUserSession(session);
    }

}
