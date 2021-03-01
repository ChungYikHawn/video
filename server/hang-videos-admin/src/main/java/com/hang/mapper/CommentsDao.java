package com.hang.mapper;

import com.hang.pojo.Comments;

public interface CommentsDao {
    int deleteByPrimaryKey(String id);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);
}