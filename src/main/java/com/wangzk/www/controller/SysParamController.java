package com.wangzk.www.controller;

import com.wangzk.www.bean.Resp;
import com.wangzk.www.bean.SysParam;
import com.wangzk.www.service.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * PackageName: com.wangzk.www.controller
 * ClassName: SysParamController
 * Description:
 * date: 2020/1/10 11:07
 *
 * @author bufou-wangzk
 */
@RestController
@Slf4j
public class SysParamController {

    @Autowired
    SysParamService sysParamService;

    @PostMapping("/saveParam")
    public Resp saveParam(@RequestBody SysParam sysParam){
        return sysParamService.saveParam(sysParam);
    }

    @GetMapping("/getParamList/{code}")
    public Resp getParamListByCode(@PathVariable(value = "code") String code){
        return sysParamService.getParamListByCode(code);
    }
}
