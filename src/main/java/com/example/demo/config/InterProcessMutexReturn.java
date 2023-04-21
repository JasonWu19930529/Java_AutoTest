package com.example.demo.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterProcessMutexReturn {

    @Autowired
    CuratorFramework curatorFramework;

    @Bean
    public InterProcessMutex interProcessMutexReturnY() {
        return new InterProcessMutex(curatorFramework, "/lock");
    }
}
