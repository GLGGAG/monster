package com.orange.service.impl;

import com.orange.entity.User;
import com.orange.mapper.TeamMapper;
import com.orange.mapper.UserMapper;
import com.orange.service.ITeamService;
import com.orange.service.IUserService;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.TeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 16:50
 * @Description:
 */
@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;



    @Override
    public ResponseMessage<List<TeamVo>> getTeamList() {
        List<TeamVo> re=teamMapper.selectTeamVoGroupByTeamNo();
        return new ResponseMessage<List<TeamVo>>(re);
    }

    @Override
    public ResponseMessage<List<User>> getCurrentTeamMember(Integer userNo,Integer teamNo) {
        List<User> users = userMapper.selectUserListByTeamNoExcludeUserNo(userNo,teamNo);
        ResponseMessage<List<User>> resp = new ResponseMessage<>();
        resp.setData(users);
        return resp;
    }
}