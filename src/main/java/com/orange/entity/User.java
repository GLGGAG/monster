package com.orange.entity;

import com.orange.enmus.UserGradeType;
import com.orange.entity.base.BaseEntity;
import com.orange.support.util.CommonUtil;

/**
 * @author: GLGGAG
 * @Date: 2017/12/04/下午 20:08
 * @Description:
 */
public class User extends BaseEntity {

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户编号
     */
    private Integer userNo;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户密码
     */
    private String passWord = "123456";

    /**
     * 用户归属的项目组编号
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
