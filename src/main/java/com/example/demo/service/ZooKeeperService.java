package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ZooKeeperService {

    private static final Logger logger = LoggerFactory.getLogger(ZooKeeperService.class);

    // 注入ZkClient bean
    @Autowired
    private CuratorFramework curatorFramework;

    /**
     * 创建永久节点
     * @param path
     * @param data
     * @throws Exception
     */
    public void createNode(String path, String data) throws Exception{
        curatorFramework.create().forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建临时节点
     * @param path
     * @param data
     * @throws Exception
     */
    public void createEphemeralNode(String path, String data) throws Exception {
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建临时有序节点
     * @param path
     * @param data
     * @throws Exception
     */
    public void crateEphemeralSequentialNode(String path, String data) throws Exception {
        curatorFramework.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 往节点种设置数据
     * @param path
     * @param data
     * @throws Exception
     */
    public void setData(String path, String data) throws Exception{
        curatorFramework.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 异步修改数据
     * @param path
     * @param data
     * @throws Exception
     */
    public void setDataAsync(String path, String data) throws Exception{
        // 添加回调监听器, set数据成功后会对节点进行监听
        CuratorListener listener = (client, event) -> {
            Stat stat = event.getStat();
            logger.info("stat=" + JSON.toJSONString(stat));
            CuratorEventType eventType = event.getType();
            logger.info("eventType="+eventType.name());
        };
        curatorFramework.getCuratorListenable().addListener(listener);
        curatorFramework.setData().inBackground().forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 删除节点
     * @param path
     * @throws Exception
     */
    public void deleteData(String path) throws Exception{
        curatorFramework.delete().forPath(path);
    }

    /**
     * 安全删除节点
     * @param path
     * @throws Exception
     */
    public void guaranteedDeleteData(String path) throws Exception {
        curatorFramework.delete().guaranteed().forPath(path);
    }

    /**
     * 获取子节点下的全部子节点路径集合
     * @param path 指定节点路径
     * @return List<String> 子节点路径集合
     * @throws Exception
     */
    public List<String> watchedGetChildren(String path) throws Exception {
        List<String> children = curatorFramework.getChildren().watched().forPath(path);
        return children;
    }


    /**
     * 获取节点数据
     * @param path 节点路径
     * @param fullClassName 数据转换对象全类名
     * @return Object
     * @throws Exception
     */
    public Object getDataByPath(String path, String fullClassName) throws Exception {
        String jsonStr = new String(curatorFramework.getData().forPath(path), StandardCharsets.UTF_8);
        Class clazz = Class.forName(fullClassName);
        return JSON.parseObject(jsonStr, clazz);
    }

}

