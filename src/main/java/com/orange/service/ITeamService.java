package com.orange.service;

import com.orange.entity.User;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.TeamVo;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 16:50
 * @Description:
 */
public interface ITeamService {

    ResponseMessage<List<TeamVo>> getTeamList();

    ResponseMessage<List<User>> getCurrentTeamMember(Integer userNo, Integer teamNo);
}
