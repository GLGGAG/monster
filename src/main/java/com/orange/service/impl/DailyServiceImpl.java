package com.orange.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.orange.entity.Daily;
import com.orange.entity.factory.DailyFactory;
import com.orange.mapper.DailyMapper;
import com.orange.service.IDailyService;
import com.orange.support.common.CommonResponse;
import com.orange.support.common.GlobalConstant;
import com.orange.support.common.LoginUserSession;
import com.orange.support.date.DateUtil;
import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.UserDailyContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 11:59
 * @Description:
 */
@Service
public class DailyServiceImpl implements IDailyService {

    @Autowired
    private DailyMapper dailyMapper;

    @Autowired
    private DailyFactory dailyFactory;

    @Override
    public ResponseMessage<PageInfo> getDailyContentVos(Integer userNo, Integer pageNum, Integer pageSize) {
        /**
         * 计算分页
         */
        PageHelper.startPage(pageNum,pageSize);
        List<Daily> userDailies=getDailyContentListByUserNo(userNo);
        PageInfo<Daily>  pageInfo=new PageInfo<>(userDailies);
        return new ResponseMessage<PageInfo>(pageInfo);
    }

    @Override
    public ResponseMessage<Object> saveDailyContent(Integer userNo,Daily daily) {

        //查找今日用户是否已经发过日报
        Daily siExistTodayDaily = GlobalConstant.dailyContentMap.get(userNo);
        if (siExistTodayDaily == null){
            Daily todayDaily=dailyMapper.selectTodayDailyContentByUserNo(userNo, DateUtil.getDayStart(new Date()));
            if (todayDaily!=null){
                daily.setId(todayDaily.getId());
                GlobalConstant.dailyContentMap.put(userNo,daily);
                return  new ResponseMessage<Object>(ErrorMsg.DailyIsExist);
            }else {
                int i=dailyMapper.insertDailyContent(userNo,daily.getTodayContent(),daily.getIncompleteCause(),daily.getMorrowPlan(),daily.getRiskPoint(),daily.getRemark());
                return CommonResponse.responseMessage(i);
            }
        }else {
            int i=dailyMapper.updateDailyContentById(siExistTodayDaily.getId(),daily.getTodayContent(),daily.getIncompleteCause(),daily.getMorrowPlan(),daily.getRiskPoint(),daily.getRemark());
            GlobalConstant.dailyContentMap.remove(userNo);
            return CommonResponse.responseMessage(i);

        }
    }



    private List<Daily> getDailyContentListByUserNo(Integer userNo) {
        return  dailyMapper.selectDailyContentListByUserNo(userNo);
    }






}
