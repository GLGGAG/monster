package com.orange.service;

import com.orange.entity.User;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.TeamUserVo;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/04/下午 20:04
 * @Description:
 */
public interface IUserService {

    ResponseMessage<Object> loginVerify(String phone, String passWord);

    ResponseMessage<Object> addUser(User user);

    ResponseMessage<Object> deleteUser(Integer userNo);

    ResponseMessage<List<TeamUserVo>> getTeamUserVo(LoginUserSession userSession);
}
