package com.example.demo.service;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZooKeeperLockOperate {

    @Resource
    InterProcessMutex interProcessMutexReturnY;

    public void acquireAndReleaseLock(){
        try {
            //加锁
            System.out.println(Thread.currentThread().getName());
            interProcessMutexReturnY.acquire();
            System.out.println(Thread.currentThread().getName() + "获取锁成功");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //释放锁
                interProcessMutexReturnY.release();
                System.out.println(Thread.currentThread().getName() + "释放锁成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
