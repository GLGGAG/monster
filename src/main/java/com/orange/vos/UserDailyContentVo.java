package com.orange.vos;

import com.github.pagehelper.PageInfo;
import com.orange.enmus.UserGradeType;
import com.orange.entity.Daily;

import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 12:01
 * @Description:用户日报内容vo
 */
public class UserDailyContentVo {

    private UserGradeType userGradeType;

    private PageInfo<Daily>   daily;

    public UserGradeType getUserGradeType() {
        return userGradeType;
    }

    public void setUserGradeType(UserGradeType userGradeType) {
        this.userGradeType = userGradeType;
    }

    public PageInfo<Daily> getDaily() {
        return daily;
    }

    public void setDaily(PageInfo<Daily> daily) {
        this.daily = daily;
    }
}
