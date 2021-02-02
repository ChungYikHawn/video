package com.hang.hangvideosdev.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * users_like_videos
 * @author 
 */
@Data
public class UsersLikeVideos implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * 用户
     */
    private String userId;

    /**
     * 视频
     */
    private String videoId;

    private static final long serialVersionUID = 1L;
}