package com.orange.controller;

import com.google.common.base.Preconditions;
import com.orange.controller.base.BaseController;
import com.orange.service.IUserService;
import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: GLGGAG
 * @Date: 2017/12/04/下午 19:56
 * @Description: 登录控制器
 * 功能：对登录进行验证
 */
@RestController
@RequestMapping("login")
public class LoginController extends BaseController{

    @Autowired
    private IUserService userService;

    @PostMapping("verify")
    public ResponseMessage<Object> loginVerify(HttpServletRequest request,HttpSession session, String phone, String passWord){
        Preconditions.checkArgument(!StringUtils.isAnyEmpty(phone,passWord),ErrorMsg.ParamsIsNull.getMsg());
        ResponseMessage<Object> resp = userService.loginVerify(phone, passWord);
        if (resp.succeed()){
            /**
             * 保存session
             */
            session.setAttribute(GlobalConstant.USER_SESSION_KEY,resp.getData());
            resp.setData(resp.getData());
        }
        return resp;
    };

    @RequestMapping("logout")
    public ResponseMessage<Object> logout(HttpSession session){
        session.removeAttribute(GlobalConstant.USER_SESSION_KEY);
        return new ResponseMessage<>();
    }

}
