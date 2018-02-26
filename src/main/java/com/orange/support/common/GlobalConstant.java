package com.orange.support.common;

import com.orange.entity.Daily;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 9:53
 * @Description:全局常亮类
 */
public class GlobalConstant {

    /**
     * 用户session  key
     */
    public static final String USER_SESSION_KEY="userSessionKey";


    /**
     * 每页数量
     */
    public static final Integer PAGE_SIZE = 3 ;

    /**
     * 日报内容
     */
    public static final ConcurrentHashMap<Integer,Daily>  dailyContentMap =new ConcurrentHashMap<>();



}
