package com.orange.service;

import com.github.pagehelper.PageInfo;
import com.orange.entity.Daily;
import com.orange.support.common.LoginUserSession;
import com.orange.support.message.ResponseMessage;
import com.orange.vos.UserDailyContentVo;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 11:59
 * @Description:
 */
public interface IDailyService {

    ResponseMessage<PageInfo> getDailyContentVos(Integer userNo, Integer pageNum, Integer pageSize);

    ResponseMessage<Object> saveDailyContent(Integer userNo, Daily daily);
}
