package com.wangzk.www.bean;

import lombok.Data;

/**
 * PackageName: com.wangzk.www.bean
 * ClassName: VideoListQry
 * Description:
 * date: 2020/1/10 15:01
 *
 * @author bufou-wangzk
 */
@Data
public class VideoListQry {
    private Integer page;
    private Integer limit;
    private String videoType;
}
