package com.wangzk.www.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * PackageName: com.wangzk.www.bean
 * ClassName: SysParam
 * Description:
 * date: 2020/1/10 11:01
 *
 * @author bufou-wangzk
 */
@Data
@Table(name = "sys_param")
@Entity
public class SysParam {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "param_code")
    private String paramCode;
    @Column(name = "param_key")
    private String paramKey;
    @Column(name = "param_val")
    private String paramVal;
}
