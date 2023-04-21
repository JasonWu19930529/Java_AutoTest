package com.example.demo.config;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;


@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redissonClient(){
        Redisson redisson = null;
        try {
            Config config = new Config();
            config.setTransportMode(TransportMode.NIO);
            SingleServerConfig singleServerConfig = config.useSingleServer();
            //可以用"rediss://"来启用SSL连接
            singleServerConfig.setAddress("redis://@127.0.0.1:6379");
            singleServerConfig.setPassword("redis");
            singleServerConfig.setDatabase(0);
            singleServerConfig.setConnectTimeout(5000000);
            redisson = (Redisson) Redisson.create(config);
        }catch (Exception e){
            System.out.println("初始化失败");
            e.printStackTrace();
        }
        return redisson;
    }
}

