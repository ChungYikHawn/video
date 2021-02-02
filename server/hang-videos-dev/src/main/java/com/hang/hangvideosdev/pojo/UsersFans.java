package com.hang.hangvideosdev.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * users_fans
 * @author 
 */
@Data
public class UsersFans implements Serializable {
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 粉丝
     */
    private String fanId;

    private static final long serialVersionUID = 1L;
}