package com.hang.mapper;

import com.hang.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersDao {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Users> usersList(@Param("username") String username, @Param("nickname") String nickname);

}