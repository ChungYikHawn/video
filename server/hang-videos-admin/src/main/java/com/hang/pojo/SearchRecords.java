package com.hang.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * search_records
 * @author 
 */
@Data
public class SearchRecords implements Serializable {
    private String id;

    /**
     * 搜索的内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}