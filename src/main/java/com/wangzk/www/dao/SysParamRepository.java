package com.wangzk.www.dao;

import com.wangzk.www.bean.SysParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PackageName: com.wangzk.www.dao
 * ClassName: SysParamRepository
 * Description:
 * date: 2020/1/10 11:05
 *
 * @author bufou-wangzk
 */
@Repository
public interface SysParamRepository extends JpaRepository<SysParam,Long>{

    List<SysParam> findByParamCodeAndParamKey(String paramCode , String paramKey);

    List<SysParam> findByParamCode(String paramCode);
}
