package com.orange.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.orange.controller.base.BaseController;
import com.orange.enmus.UserGradeType;
import com.orange.entity.Daily;
import com.orange.service.IDailyService;
import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.support.util.SessionHelper;
import com.orange.vos.UserDailyContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author:GLGGAG
 * @Date:2017/10/19
 * @Description:主页控制器
 */
@RestController
@RequestMapping("daily")
public class DailyController extends BaseController{


    @Autowired
    private IDailyService iDailyService;


    @RequestMapping("list")
    public ResponseMessage<PageInfo> dailyContent(Integer userNo,Integer pageNum, Integer pageSize){
        Preconditions.checkArgument(userNo != null, ErrorMsg.ParamsIsNull.getMsg(),userNo);
        if ( pageNum == null)pageNum = 1;
        if ( pageSize == null)pageSize = GlobalConstant.PAGE_SIZE;
        return iDailyService.getDailyContentVos(userNo,pageNum,pageSize);
    }

    @RequestMapping("save")
    public ResponseMessage<Object> saveDailyContent(HttpSession session, Daily daily){
        LoginUserSession userSession = SessionHelper.getLoginUserSession(session);
        return iDailyService.saveDailyContent(userSession.getUserNo(),daily);
    }



}
