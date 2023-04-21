package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RegService {


    // 发送短信，用异步进行处理和标记
    @Async("taskExecutor02")
    public void sendMsg(){
        // todo :模拟耗时5秒
        try {
            Thread.sleep(5000);
            log.info("---------------发送消息--------");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    // 添加积分，用异步进行处理和标记
    @Async("taskExecutor02")
    public void addScore(){
        // todo :模拟耗时5秒
        try {
            Thread.sleep(5000);
            log.info("---------------处理积分--------");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
