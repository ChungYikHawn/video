package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersDao {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    int selectUsername(String username);

    Users selectLogin(@Param("username") String username, @Param("password") String password);

    Users queryUserInfo(String userId);

    int addReceiveLikeCount(String videoCreaterId);

    int subReceiveLikeCount(String videoCreaterId);


    int subFansCount(String userId);

    int addFansCount(String userId);

    int addFollowersCount(String fanId);

    int subFollowersCount(String fanId);
}