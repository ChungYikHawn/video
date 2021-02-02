package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.UsersReport;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersReportDao {
    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    int insertSelective(UsersReport record);

    UsersReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersReport record);

    int updateByPrimaryKey(UsersReport record);
}