package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.Comments;
import com.hang.hangvideosdev.pojo.vo.CommentsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentsDao {
    int deleteByPrimaryKey(String id);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);

    List<CommentsVO> queryComments(String videoId);
}