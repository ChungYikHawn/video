package com.hang.hangvideosdev.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * users
 * @author 
 */
@Data
@ApiModel(value = "用户对象")
public class Users implements Serializable {
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",name = "username",required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",name = "password",required = true)
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /**
     * 昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickname;

    /**
     * 我的粉丝数量
     */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

    private static final long serialVersionUID = 1L;
}