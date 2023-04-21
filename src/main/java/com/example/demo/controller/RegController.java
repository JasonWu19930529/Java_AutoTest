package com.example.demo.controller;

import com.example.demo.service.RegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: xuke
 * @time: 2021/6/23 20:10
 */

@RestController
@Slf4j
public class RegController {


    @Autowired
    private RegService regService;

    /**
     * @Author xuke
     * @Description 用户注册
     * @Date 20:11 2021/6/23
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/reg")
    public String reguser(){
        // 1: 注册用户 10ms
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间
        Date beginTime = new Date();
        //利用时间格式，把当前时间转为字符串
        String start = df.format(beginTime);
        //当前时间 转为 长整型 Long
        Long begin = beginTime.getTime();

        log.info("新用户注册");
        //userService.save(user);

        // 2: 发送短信 5s
        log.info("发送短信");
        regService.sendMsg();

        // 3: 添加积分 5s
        log.info("添加积分");
        regService.addScore();

        Date finishTime = new Date();
        //结束时间 转为 Long 类型
        Long end = finishTime.getTime();
        long timeLag = end - begin;

        long day=timeLag/(24*60*60*1000);
        //小时
        long hour=(timeLag/(60*60*1000)-day*24);
        //分钟
        long minute=((timeLag/(60*1000))-day*24*60-hour*60);

        return "ok" + (timeLag/1000-day*24*60*60-hour*60*60-minute*60);
    }
}

