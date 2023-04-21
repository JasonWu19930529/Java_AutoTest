package com.example.demo.publicClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guoce
 * @date 2022/11/30 11:24
 * note : 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private Integer code;
    private String message;
    private Object obj;

    /**
     * 成功返回结果
     * @param message message
     * @return RespBean
     */
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    public static RespBean success(Object obj){
        return new RespBean(200,null,obj);
    }

    /**
     * 成功返回结果
     * @param message message
     * @param obj obj
     * @return RespBean
     */
    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }

    /**
     * 失败返回结果
     * @param message message
     * @return RespBean
     */
    public static RespBean error(String message){
        return new RespBean(418,message,null);
    }

    /**
     * 失败返回结果
     * @param message message
     * @param obj obj
     * @return RespBean
     */
    public static RespBean error(String message,Object obj){
        return new RespBean(418,message,obj);
    }
}

