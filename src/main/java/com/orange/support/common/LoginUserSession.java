package com.orange.support.common;

import com.orange.enmus.UserGradeType;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 9:56
 * @Description:登录用户session 信息类
 */
public class LoginUserSession {

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户编号
     */
    private Integer  userNo;

    /**
     * 所属项目组
     */
    private Integer teamNo;

    /**
     * 用户等级身份
     */
    private UserGradeType userGradeType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public Integer getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(Integer teamNo) {
        this.teamNo = teamNo;
    }

    public UserGradeType getUserGradeType() {
        return userGradeType;
    }

    public void setUserGradeType(UserGradeType userGradeType) {
        this.userGradeType = userGradeType;
    }
}
