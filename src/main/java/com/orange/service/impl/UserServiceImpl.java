package com.orange.service.impl;

import com.orange.enmus.TeamType;
import com.orange.enmus.UserGradeType;
import com.orange.entity.Team;
import com.orange.entity.User;
import com.orange.mapper.TeamMapper;
import com.orange.mapper.UserMapper;
import com.orange.service.ITeamService;
import com.orange.service.IUserService;
import com.orange.support.common.CommonResponse;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.support.util.CommonUtil;
import com.orange.vos.TeamUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/04/下午 20:06
 * @Description:
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ITeamService iTeamService;


    @Override
    public ResponseMessage<Object> loginVerify(String phone, String passWord) {
        User user = getUserByUserPhone(phone);
        if (user==null){
            return  new ResponseMessage<>(ErrorMsg.UserInexistence);
        }
        if (StringUtils.equalsIgnoreCase(passWord,user.getPassWord())){
            LoginUserSession userSession = new LoginUserSession();
            userSession.setUserName(user.getUserName());
            userSession.setUserNo(user.getUserNo());
            userSession.setTeamNo(user.getTeamNo());
            userSession.setUserGradeType(user.getUserGradeType());
            return  new ResponseMessage<Object>(userSession);
        }
        return new ResponseMessage<>(ErrorMsg.UserLoginError);
    }

    @Transactional
    @Override
    public ResponseMessage<Object> addUser(User user) {
        String phone = user.getPhone();
        User isExitUser = getUserByUserPhone(phone);
        if (isExitUser != null){
            return new ResponseMessage<>(ErrorMsg.UserExist);
        }
        Integer userNo = Integer.valueOf(CommonUtil.random(8));
        UserGradeType gradeType = user.getUserGradeType();
        if (gradeType !=UserGradeType.ManagerCenter){
            Team team = new Team();
            team.setUserNo(userNo);
            team.setTeamName(TeamType.getTeamName(user.getTeamNo()));
            team.setTeamNo(user.getTeamNo());
            teamMapper.insertTeam(team);
        }
        user.setUserNo(userNo);
        int i=userMapper.insertUser(user);
        return CommonResponse.responseMessage(i);
    }

    @Transactional
    @Override
    public ResponseMessage<Object> deleteUser(Integer userNo) {
        //删除该用户所属的项目组
        User user = userMapper.selectUserByUserNo(userNo);
        if (user == null){
            return CommonResponse.responseMessage(0);
        }
        int i=userMapper.deletedUserByUserNo(userNo);
        if (i == 0){
            return CommonResponse.responseMessage(0);
        }
        int j=teamMapper.deletedUserByUserNoTeamNo(userNo,user.getTeamNo());
        return CommonResponse.responseMessage(j);
    }

    @Override
    public ResponseMessage<List<TeamUserVo>> getTeamUserVo(LoginUserSession userSession) {
        ResponseMessage<List<TeamUserVo>> resp=new ResponseMessage<List<TeamUserVo>>();
        //如果是组长
        if (UserGradeType.TeamLeader == userSession.getUserGradeType()){
            List<User> users = iTeamService.getCurrentTeamMember(userSession.getUserNo(), userSession.getTeamNo()).getData();
            if (users.size()>0){
                String teamName = TeamType.getTeamName(userSession.getTeamNo());
                TeamUserVo vo = new TeamUserVo();
                List<TeamUserVo> vos=new ArrayList<TeamUserVo>(2);
                vo.setTeamName(teamName);
                vo.setTeamUser(users);
                vos.add(vo);
                resp.setData(vos);
            }
        }else{
            List<TeamUserVo> teamUserVos=userMapper.selectTeamUserList();
            //筛选当某个项目组无队员时清洗掉
            List<TeamUserVo> synTeamUserVos = Collections.synchronizedList(teamUserVos);
            for (int i=0; i<synTeamUserVos.size() ; i++){
                List<User> teamUser = synTeamUserVos.get(i).getTeamUser();
                String teamName = synTeamUserVos.get(0).getTeamName();
                if (teamUser!=null && teamUser.size()>0){
                    if (TeamType.development.getTeamName().equalsIgnoreCase(teamName)){
                        List<User> syncTeamUserList = Collections.synchronizedList(teamUser);
                        for (int j=0;j<syncTeamUserList.size();j++){
                            User user = syncTeamUserList.get(0);
                            if (userSession.getUserNo().equals(user.getUserNo())){
                                syncTeamUserList.remove(j);
                            }
                        }
                    }
                }
            }
            for (int i=0;i<synTeamUserVos.size() ; i++){
                List<User> teamUser = synTeamUserVos.get(i).getTeamUser();
                if (teamUser!=null && teamUser.size()>0){
                    continue;
                }
                synTeamUserVos.remove(i);
            }
            resp.setData(synTeamUserVos);
        }
        return resp;
    }


    private User getUserByUserPhone(String userPhone){
        return userMapper.selectUserByUserPhone(userPhone);
    }




}
