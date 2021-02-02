package com.hang.hangvideosdev.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * users_report
 * @author 
 */
@Data
public class UsersReport implements Serializable {
    private String id;

    /**
     * 被举报用户id
     */
    private String dealUserId;

    private String dealVideoId;

    /**
     * 类型标题，让用户选择，详情见 枚举
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 举报人的id
     */
    private String userid;

    /**
     * 举报时间
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;
}