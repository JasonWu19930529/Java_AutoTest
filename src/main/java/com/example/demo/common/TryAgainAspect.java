package com.example.demo.common;

import com.example.demo.specialAnnotation.FailRetry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Aspect
@Configuration
@Slf4j
public class TryAgainAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.example.demo.specialAnnotation.FailRetry)")
    private void failRetryPointCut() {

    }

    @Around("failRetryPointCut() && @annotation(failRetry)")
    @Transactional(rollbackFor = Exception.class)
    public Object retry(ProceedingJoinPoint joinPoint, FailRetry failRetry) throws Throwable {
        int count = 0;
        do {
            count++;
            try {
                log.info("重试次数：{}", count);
                return joinPoint.proceed();
            } catch (TryAgainException e) {
                if(count >= failRetry.value()){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new TryAgainException("重试失败");
                }
            }
        } while (true);
    }
}
