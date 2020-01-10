package com.wangzk.www.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: PageBean
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.dto
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-11 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBean<T> {
    private List<T> content;  //内容列表

    private int size ;  //每页大小

    private int elementTotalSize;   //list中元素有多少个

    private int page; //当前页数

    private int totalPage;   //总的页数

    private int totalSize;   //总共的数量

}
