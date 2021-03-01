package com.hang.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * bgm
 * @author 
 */
@Data
public class Bgm implements Serializable {
    private String id;

    private String author;

    private String name;

    /**
     * 播放地址
     */
    private String path;

    private static final long serialVersionUID = 1L;
}