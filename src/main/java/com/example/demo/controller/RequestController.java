package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.Student;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.LeGuan;
import com.example.demo.service.ZooKeeperLockOperate;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class RequestController {

    @Autowired
    UserMapper userMapper;

//    @Autowired
//    @Qualifier(value = "sqlSessionReturn")
//    @Resource(name = "sqlSessionReturn")
//    SqlSession sqlSession01;

//    @Autowired
//    SqlSession sqlSessionTemplate;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

//先按照类型，再按照名字
//    @Autowired
//    @Qualifier(value = "sqlSessionReturn01")
//    SqlSession sqlSession;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    @Qualifier(value = "threadPoolTaskExecutorReturn")
    Executor threadPoolTaskExecutor;

    @Autowired
    CuratorFramework curatorFrameworkReturn;

    @Resource
    InterProcessLock interProcessMutexReturnY;

    @Resource
    ZooKeeperLockOperate zooKeeperLockOperate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LeGuan leGuan;


    @RequestMapping(value="/index01", method = RequestMethod.GET)
    @ResponseBody
    public String index01(){
        return "成功";
    }

    @RequestMapping(value = "/index02", method = RequestMethod.GET)
    @ResponseBody
    public Student index02(){
        Student student = userMapper.selectUserList("wurui");
        log.info(Thread.currentThread().getName() + " 数据库查询结果为： " + student);
        return student;
    }

//    @RequestMapping(value = "/index03", method = RequestMethod.GET)
//    @ResponseBody
//    public Student index03() {
////        SqlSession sqlSession = sqlSessionConfig.sqlSessionReturn();
//
//        Student student = null;
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("name", "wurui");
//        hashMap.put("age", 5000);
//        hashMap.put("favorite", "乒乓球-足球-篮球-排球-垒球20230327");
//        try {
////            sqlSession.selectOne("com.example.demo.mapper.UserMapper.selectUserList", "wurui");
//            sqlSession.update("com.example.demo.mapper.UserMapper.updateStudent", hashMap);
//            Thread.sleep(5);
////            student = sqlSession.selectOne("com.example.demo.mapper.UserMapper.selectUserList", "wurui");
////            log.info("数据库查询结果为： " + student);
////            Thread.sleep(20000);
////            sqlSession.commit();
////            sqlSession.close();
//        }catch (InterruptedException e){
//            // 一个事务在执行过程中发生错误，将进行回滚操作
//            sqlSession.rollback();
//        }
//        return student;
//    }

    @RequestMapping(value = "/index04", method = RequestMethod.GET)
    @ResponseBody
    public String index04() {
        String lockKey = "numKey";
        RLock rLock = redissonClient.getLock(lockKey);
        try{
            log.info("***开始执行***");
            rLock.lock(3, TimeUnit.SECONDS);
            int stock = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get("num")));
            log.info("现有库存数量： " + stock);
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("num", String.valueOf(realStock));
                log.info(Thread.currentThread().getName() + " 成功扣减库存数量，且目前库存数量为: " + realStock);
            }else {
                log.info(Thread.currentThread().getName() + "扣除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            rLock.unlock();
        }
        return "请求成功";
    }

    @RequestMapping(value = "/index05", method = RequestMethod.GET)
    @ResponseBody
    public String index05() {
        try {
            //加锁
            interProcessMutexReturnY.acquire();
            System.out.println(Thread.currentThread().getName() + "获取锁成功");
            Thread.sleep(30000);
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
        return "请求成功" + System.currentTimeMillis();
    }

    @RequestMapping(value = "/index06", method = RequestMethod.GET)
    @ResponseBody
    public String index06() {
        zooKeeperLockOperate.acquireAndReleaseLock();
        return "请求成功" + System.currentTimeMillis();
    }

//    @RequestMapping(value = "/index08", method = RequestMethod.GET)
//    @ResponseBody
//    public Student index08() {
//        Student student = null;
//        try {
//            student = sqlSession.selectOne("com.example.demo.mapper.UserMapper.selectUserList01", "wurui");
//            log.info("数据库查询结果为： " + student);
//            System.out.println(student);
//            Thread.sleep(50000);
//            sqlSession.clearCache();
//            TimeUnit.SECONDS.sleep(5);
//            student = sqlSession.selectOne("com.example.demo.mapper.UserMapper.selectUserList01", "wurui");
//            log.info("数据库查询结果为： " + student);
//            System.out.println(student);
//            sqlSession.commit();
//        }catch (InterruptedException e){
//            // 一个事务在执行过程中发生错误，将进行回滚操作
//            sqlSession.rollback();
//        }
//        return student;
//    }

//    @RequestMapping(value = "/index09", method = RequestMethod.GET)
//    @ResponseBody
//    public String index09() {
//        String lockKey = "numKey";
//        RLock rLock = redissonClient.getLock(lockKey);
//        try{
////            log.info("***开始执行***");
//            rLock.lock(3, TimeUnit.SECONDS);
//            Product product = sqlSession.selectOne("com.example.demo.mapper.UserMapper.selectProduct","篮球");
////            log.info(product.toString());
//            if (product.getNum() > 0) {
//                int realStock = product.getNum() - 1;
//                HashMap<String, Object> hashMap = new HashMap<>();
//                hashMap.put("id", product.getId());
//                hashMap.put("productName", product.getProductName());
//                hashMap.put("num", realStock);
//                sqlSession.update("com.example.demo.mapper.UserMapper.updateProduct", hashMap);
//                log.info(Thread.currentThread().getName() + " 成功扣减库存数量，且目前库存数量为: " + realStock);
//                sqlSession.commit();
//            }else {
//                log.info(Thread.currentThread().getName() + "扣除失败");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            sqlSession.rollback();
//        }finally {
//            rLock.unlock();
//        }
//        return "请求成功";
//    }

    @RequestMapping(value = "/index10", method = RequestMethod.GET)
    @ResponseBody
    public String index10(@RequestParam("version") Integer version) {
            try{
                Product product = sqlSessionTemplate.selectOne("com.example.demo.mapper.UserMapper.selectProduct","篮球");
//                log.info("此时获取产品的版本号： " + product.getVersion());
//                log.info("请求参数中产品的版本号： " + version);
                if (product.getNum() > 0) {
                    int realStock = product.getNum() - 1;
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", product.getId());
                    hashMap.put("productName", product.getProductName());
                    hashMap.put("num", realStock);
                    hashMap.put("version", version);
                    hashMap.put("oldVersion", version);
                    sqlSessionTemplate.update("com.example.demo.mapper.UserMapper.updateProductLeG", hashMap);
                    Product productNew = sqlSessionTemplate.selectOne("com.example.demo.mapper.UserMapper.selectProduct","篮球");
                    log.info("最新库存数量： " + productNew.getNum());
//                    sqlSessionTemplate.commit();
//                    TimeUnit.SECONDS.sleep(5);
                }else {
                    log.info(Thread.currentThread().getName() + "扣除失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                sqlSessionTemplate.rollback();
            }
            return "请求成功";
    }

    @RequestMapping(value = "/index11", method = RequestMethod.GET)
    @ResponseBody
    public String index10(@RequestParam("version") Integer version,
                          @RequestParam("need") Integer need) {
        return leGuan.updateGoodsQuantity(version,need);
    }
}
