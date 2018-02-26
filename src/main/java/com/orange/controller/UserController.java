package com.orange.controller;

import com.google.common.base.Preconditions;
import com.orange.controller.base.BaseController;
import com.orange.enmus.UserGradeType;
import com.orange.entity.User;
import com.orange.service.IUserService;
import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.TeamUserVo;
import com.orange.vos.UserGradeTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/06/上午 9:47
 * @Description:
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping("add")
    public ResponseMessage<Object>  addUser(User user){
        Preconditions.checkArgument(user != null ||user.getTeamNo() != null ,ErrorMsg.ParamsIsNull.getMsg());
        Preconditions.checkArgument(user.getPhone() != null,"手机号码不能为空");
        return userService.addUser(user);
    }


    @RequestMapping("delete")
    public ResponseMessage<Object> deleteUser(Integer userNo){
        Preconditions.checkArgument(userNo != null,"用户编号不能为空");
        return userService.deleteUser(userNo);
    }

    @RequestMapping("list")
    public ResponseMessage<List<TeamUserVo>> getTeamUserListVo(HttpSession session){
        LoginUserSession userSession = userSession(session);
        return userService.getTeamUserVo(userSession);

    }


    @RequestMapping("info")
    public ResponseMessage<Object>  getUserInfo(HttpSession session){
        LoginUserSession userSession = userSession(session);
        return new ResponseMessage<Object>(userSession);
    }


    @RequestMapping("config")
    public ResponseMessage<List<UserGradeTypeVo>>  getUserConfig(Integer teamNo){
        Preconditions.checkArgument(teamNo != null,"teamNo  不能为空");
        List<UserGradeTypeVo> vos = new ArrayList<>();
        if (teamNo==0){
            UserGradeTypeVo vo1 = new UserGradeTypeVo();
            vo1.setName(UserGradeType.ManagerCenter.getName());
            vo1.setType(UserGradeType.ManagerCenter);
            vos.add(vo1);
            UserGradeTypeVo vo2 = new UserGradeTypeVo();
            vo2.setName(UserGradeType.DepartmentHeads.getName());
            vo2.setType(UserGradeType.DepartmentHeads);
            vos.add(vo2);
            return new ResponseMessage<>(vos);
        }
        UserGradeTypeVo vo1 = new UserGradeTypeVo();
        vo1.setName(UserGradeType.TeamLeader.getName());
        vo1.setType(UserGradeType.TeamLeader);
        vos.add(vo1);
        UserGradeTypeVo vo2 = new UserGradeTypeVo();
        vo2.setName(UserGradeType.Employee.getName());
        vo2.setType(UserGradeType.Employee);
        vos.add(vo2);
        return new ResponseMessage<>(vos);


    }

    @RequestMapping("layout")
    public ResponseMessage<Object> layout(HttpSession session){
        session.removeAttribute(GlobalConstant.USER_SESSION_KEY);
        return new ResponseMessage<>();
    }






}
