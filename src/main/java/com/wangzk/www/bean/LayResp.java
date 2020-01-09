package com.wangzk.www.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * PackageName: com.wangzk.www.bean
 * ClassName: Resp
 * Description:
 * date: 2020/1/7 15:10
 *
 * @author bufou-wangzk
 */
@Data
public class LayResp implements Serializable {
    private int code = 0;
    private String msg = "请求成功";
    private int count;
    private List data;

    public LayResp(){

    }

    public LayResp(List data){
        this.data = data;
    }

    public LayResp(int code , String msg ,int count, List data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public LayResp(int code , String msg){
        this.code = code;
        this.msg = msg;
    }
}
