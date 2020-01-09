package com.wangzk.www.service;

import com.wangzk.www.bean.LayResp;
import com.wangzk.www.bean.Resp;
import com.wangzk.www.bean.VideoItem;
import com.wangzk.www.dao.VideoItemRepository;
import com.wangzk.www.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

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

    public List<VideoItem> getVideoItemList(){
        return videoItemRepository.getVideoItemList();
    }

    public LayResp getVideoItemListForLay(){
        List<VideoItem> videoItemList = videoItemRepository.getVideoItemList();
        return new LayResp(0 , "请求成功" ,videoItemList.size() ,videoItemList);
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
}
