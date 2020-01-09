package com.wangzk.www.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * PackageName: com.wangzk.www.bean
 * ClassName: VideoItem
 * Description:
 * date: 2020/1/7 14:53
 *
 * @author bufou-wangzk
 */
@Data
@Entity
@Table(name = "video")
@EntityListeners(AuditingEntityListener.class)
public class VideoItem {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "video_id",length = 100)
    private String videoId;
    @Column(name = "video_title")
    private String videoTitle;
    @Column(name = "video_abstract")
    private String videoAbstract;
    @Column(name = "video_url")
    private String videoUrl;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "video_time")
    private Date videoTime;
}
