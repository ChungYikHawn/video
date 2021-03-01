package com.hang.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * comments
 * @author 
 */
@Data
public class Comments implements Serializable {
    private String id;

    private String fatherCommentId;

    private String toUserId;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 留言者，评论的用户id
     */
    private String fromUserId;

    /**
     * 评论内容
     */
    private String comment;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}