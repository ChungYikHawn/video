package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.UsersFans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersFansDao {
    int deleteByPrimaryKey(String id);

    int insert(UsersFans record);

    int insertSelective(UsersFans record);

    UsersFans selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersFans record);

    int updateByPrimaryKey(UsersFans record);

    int deleteByUAF(@Param("userId") String userId, @Param("fanId") String fanId);

    int queryIsFollow(@Param("userId")String userId, @Param("fanId")String fanId);
}