package com.orange.support.common;

import com.orange.support.message.ErrorMsg;
import com.orange.support.message.ResponseMessage;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 14:41
 * @Description:
 * 通用响应消息
 */
public class CommonResponse {

    public static ResponseMessage<Object> responseMessage(int i){
        if (i == 0){
            return  new ResponseMessage<Object>(ErrorMsg.SystemErr);
        }
        return  new ResponseMessage<Object>();
    }

}
