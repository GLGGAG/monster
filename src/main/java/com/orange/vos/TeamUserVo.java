package com.orange.vos;

import com.orange.entity.User;

import java.util.List;

public class TeamUserVo {

    /**
     * 当前团队名称
     */
    private String teamName;
    /**
     * 团队编号
     */
    private String teamNo;
    /**
     * 团队成员
     */
    private List<User>  teamUser;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<User> getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(List<User> teamUser) {
        this.teamUser = teamUser;
    }
}
