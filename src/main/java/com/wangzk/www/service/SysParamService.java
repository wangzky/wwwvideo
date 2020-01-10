package com.wangzk.www.service;

import com.wangzk.www.bean.Resp;
import com.wangzk.www.bean.SysParam;
import com.wangzk.www.dao.SysParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PackageName: com.wangzk.www.service
 * ClassName: SysParamService
 * Description:
 * date: 2020/1/10 11:09
 *
 * @author bufou-wangzk
 */
@Service
public class SysParamService {

    @Autowired
    SysParamRepository sysParamRepository;

    public Resp saveParam(SysParam sysParam){

        List<SysParam> byParamCodeAndParamKey = sysParamRepository.findByParamCodeAndParamKey(sysParam.getParamCode(), sysParam.getParamKey());
        if (byParamCodeAndParamKey.size()> 0){
            return new Resp("9999" , "已有值");
        }

        sysParamRepository.save(sysParam);

        return new Resp();
    }

    public Resp getParamListByCode(String code){
        List<SysParam> byParamCode = sysParamRepository.findByParamCode(code);
        return new Resp(byParamCode);
    }
}
