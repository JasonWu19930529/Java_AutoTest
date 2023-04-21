package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.JedisPoolEConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RequestMapping("/Mapping")
@Controller
public class RequestControllerMap {

    @Resource
    JedisPool redisPoolFactory;

    @Resource
    JedisPoolEConfig jedisPoolEConfig;
    // jedis操作redis，操作集合set
    @RequestMapping(value = "index01",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject index01(){
        Jedis jedis = redisPoolFactory.getResource();
        jedis.sadd("JasonWu", String.valueOf(22));
        jedis.close();
        Set<String> stringSet = jedis.smembers("JasonWu");
        JSONObject jsonObject = null;
        for (String str : stringSet){
            jsonObject =  JSON.parseObject(str);
            break;
        }
        return jsonObject;
    }

    @RequestMapping(value = "index02",method = RequestMethod.GET)
    @ResponseBody
    public List<String> index02(){
        Jedis jedis = redisPoolFactory.getResource();
        jedis.lpush("SHANGHAI", "021");
        jedis.close();
        return jedis.lrange("SHANGHAI", 0, jedis.llen("SHANGHAI"));
    }
}
