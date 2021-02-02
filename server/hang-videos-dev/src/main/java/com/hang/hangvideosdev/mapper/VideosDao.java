package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.Videos;
import com.hang.hangvideosdev.pojo.vo.VideosVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VideosDao {
    int deleteByPrimaryKey(String id);

    int insert(Videos record);

    int insertSelective(Videos record);

    Videos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Videos record);

    int updateByPrimaryKey(Videos record);

    List<VideosVO> queryAllVideos(@Param(value = "videoDesc") String desc, String userId);

    int addVideoLikeCount(String videoId);

    int subVideoLikeCount(String videoId);

    List<VideosVO> queryMyLikeVideos(String userId);

    List<VideosVO> queryMyFollowVideos(String userId);
}