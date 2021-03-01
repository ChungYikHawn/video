package com.hang.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * users_like_videos
 * @author 
 */
@Data
public class UsersLikeVideos implements Serializable {
    private String id;

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