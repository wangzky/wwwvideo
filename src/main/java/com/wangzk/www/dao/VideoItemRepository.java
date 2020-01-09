package com.wangzk.www.dao;

import com.wangzk.www.bean.VideoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PackageName: com.wangzk.www.dao
 * ClassName: VideoItemRepository
 * Description:
 * date: 2020/1/7 15:00
 *
 * @author bufou-wangzk
 */
@Repository
public interface VideoItemRepository extends JpaRepository<VideoItem , String> {
    @Query(nativeQuery = true,
    value = "select * from video order by video_time desc ")
    List<VideoItem> getVideoItemList();

    VideoItem getByVideoId(String videoId);

    List<VideoItem> getByVideoTitle (String videoTitle);
}
