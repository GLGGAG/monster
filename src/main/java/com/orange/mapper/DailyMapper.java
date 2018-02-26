package com.orange.mapper;

import com.orange.entity.Daily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 12:08
 * @Description:
 */
public interface DailyMapper {

    List<Daily> selectDailyContentListByUserNo(@Param("userNo") Integer userNo);

    int updateDailyContentById(@Param("id")Integer id,  @Param("todayContent") String todayContent,
                               @Param("incompleteCause") String incompleteCause, @Param("morrowPlan") String morrowPlan,
                               @Param("riskPoint") String riskPoint, @Param("remark") String remark);

    int insertDailyContent(@Param("userNo") Integer userNo, @Param("todayContent") String todayContent,
                           @Param("incompleteCause") String incompleteCause, @Param("morrowPlan") String morrowPlan,
                           @Param("riskPoint") String riskPoint, @Param("remark") String remark);

    Daily selectTodayDailyContentByUserNo(@Param("userNo") Integer userNo,@Param("date")Date date);

}
