package com.orange.entity.factory;

import com.orange.entity.Daily;
import org.springframework.stereotype.Component;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/下午 14:01
 * @Description:
 */
@Component
public class DailyFactory implements BaseFactory<Daily> {

    @Override
    public Daily getInstance() {
        Daily daily = new Daily();
        return daily;
    }

}
