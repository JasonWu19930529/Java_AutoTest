package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class CustomizeThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutorReturn() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列容量
        executor.setQueueCapacity(50);
        //活跃时间
        executor.setKeepAliveSeconds(10);
        //线程名字前缀
        executor.setThreadNamePrefix("customize-thread-");
     /*
      当poolSize已达到maxPoolSize，如何处理新任务（是拒绝还是交由其它线程处理）
      CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
     */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
