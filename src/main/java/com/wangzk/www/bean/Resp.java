package com.wangzk.www.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * PackageName: com.wangzk.www.bean
 * ClassName: Resp
 * Description:
 * date: 2020/1/7 15:10
 *
 * @author bufou-wangzk
 */
@Data
public class Resp implements Serializable {
    private String code = "0000";
    private String msg = "请求成功";
    private Object data;

    public Resp(){

    }

    public Resp(Object data){
        this.data = data;
    }

    public Resp(String code , String msg , Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Resp(String code , String msg){
        this.code = code;
        this.msg = msg;
    }
}
