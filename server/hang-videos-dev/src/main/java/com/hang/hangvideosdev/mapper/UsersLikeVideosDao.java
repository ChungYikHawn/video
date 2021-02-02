package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.UsersLikeVideos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersLikeVideosDao {
    int deleteByPrimaryKey(String id);

    int insert(UsersLikeVideos record);

    int insertSelective(UsersLikeVideos record);

    UsersLikeVideos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersLikeVideos record);

    int updateByPrimaryKey(UsersLikeVideos record);

    int deleteByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId);

    int isUserLikeVideo(@Param("loginUserId") String loginUserId, @Param("videoId")String videoId);
}