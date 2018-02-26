package com.orange.controller;

import com.orange.controller.base.BaseController;
import com.orange.enmus.TeamType;
import com.orange.entity.User;
import com.orange.service.ITeamService;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.TeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 14:56
 * @Description:
 * 项目组团队控制器
 */
@RestController
@RequestMapping("team")
public class TeamController extends BaseController{


    @Autowired
    private ITeamService iTeamService;


    @RequestMapping("list")
    public ResponseMessage<List<TeamVo>> getTeamList(){
        return iTeamService.getTeamList();
    }


    @RequestMapping("member")
    public ResponseMessage<List<User>> getCurrentTeamMember(HttpSession session,Integer teamNo){
        LoginUserSession userSession = userSession(session);
        return  iTeamService.getCurrentTeamMember(userSession.getUserNo(),teamNo);
    }

    @RequestMapping("config")
    public ResponseMessage<List<TeamVo>>  getTeamConfig(){
        TeamType[] values = TeamType.values();
        List<TeamVo> vos = new ArrayList<>();
        for (TeamType type : values) {
            TeamVo vo = new TeamVo();
            vo.setTeamNo(type.getTeamNo());
            vo.setTeamName(type.getTeamName());
            vos.add(vo);
        }
        return new ResponseMessage<>(vos);
    }

}
