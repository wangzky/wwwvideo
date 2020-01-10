package com.wangzk.www.service;

import com.wangzk.www.bean.*;
import com.wangzk.www.dao.VideoItemRepository;
import com.wangzk.www.util.JdbcUtil;
import com.wangzk.www.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * PackageName: com.wangzk.www.service
 * ClassName: VideoService
 * Description:
 * date: 2020/1/7 15:04
 *
 * @author bufou-wangzk
 */
@Service
@Slf4j
public class VideoService {

    @Autowired
    VideoItemRepository videoItemRepository;

    @Autowired
    JdbcUtil jdbcUtil;

    public List<VideoItem> getVideoItemList(){
        return videoItemRepository.getVideoItemList();
    }

    public LayResp getVideoItemListForLay(VideoListQry input){
        Integer page = input.getPage();
        Integer limit = input.getLimit();

        String videoType = input.getVideoType();

        String sql = "select * from video where 1=1  ";
        String countSql = "select count(*) from video where 1=1 ";

        if (! StringUtils.isEmpty(videoType)){
            String s = " and video_type = " + videoType + " ";
            sql += s;
            countSql += s;
        }
        PageBean pageDto = jdbcUtil.getCustomerPageDto(
                sql,
                new Object[]{},
                countSql,
                new Object[]{},
                page,
                limit);

        return new LayResp(0 , "请求成功" ,pageDto.getTotalSize() ,pageDto.getContent());
    }

    public VideoItem getVideoItemById(String videoId){
        try {
            VideoItem byVideoId = videoItemRepository.getByVideoId(videoId);
//            String videoUrl = byVideoId.getVideoUrl();
//            Base64.Encoder  encoder = Base64.getEncoder();
//            String encodedText = encoder.encodeToString(videoUrl.getBytes("UTF-8"));
//
//            byVideoId.setVideoUrl(encodedText);
            return byVideoId;
        } catch (Exception e) {
            log.error("查询失败" , videoId);
            e.printStackTrace();
            return null;
        }
    }

    public Resp addVideoItem(VideoItem videoItem){
        log.info("录入数据[{}]" , videoItem.toString());

        List<VideoItem> byVideoTitle = videoItemRepository.getByVideoTitle(videoItem.getVideoTitle());
        if (byVideoTitle.size() > 0){
            return new Resp("9999" , "录入失败, 名称重复" );
        }

        try {
            VideoItem save = videoItemRepository.save(videoItem);
            return new Resp(save.getVideoId());
        } catch (Exception e) {
            e.printStackTrace();
            return new Resp("9999" , "录入失败"  , e.getMessage());
        }
    }


    public Resp updateVideoInfo(Map input){
        log.info("修改数据[{}]" , input.toString());
        String videoId = (String) input.get("video_id");
        String field = (String) input.get("field");
        String value = (String) input.get("value");

        if (StringUtils.isEmpty(videoId) || StringUtils.isEmpty(field) || StringUtils.isEmpty(value)){
            return new Resp("9999" , "数据不完整");
        }

        String sql = "update video set " + field + " = '" + value +"' where video_id = '"+ videoId + "';" ;
        jdbcUtil.jdbcUpdate(sql);

        return new Resp();

    }

    public Resp delVideo(String videoId){
        if (StringUtils.isEmpty(videoId)){
            return new Resp("9999" , "id为空");
        }

        videoItemRepository.deleteById(videoId);

        return new Resp();
    }
}
