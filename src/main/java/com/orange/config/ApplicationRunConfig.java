package com.orange.config;

import com.orange.mapper.PositionMapper;
import com.orange.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: GLGGAG
 * @Date: 2017/12/05/上午 11:24
 * @Description:通用sql配置  当项目启动时需要读取一些常用信息加入常亮类里面全局共用
 */
@Component
public class ApplicationRunConfig implements CommandLineRunner {


    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PositionMapper positionMapper;


    @Override
    public void run(String... strings) throws Exception {



    }




}
