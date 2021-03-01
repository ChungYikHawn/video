package com.hang.mapper;

import com.hang.pojo.UsersReport;
import com.hang.pojo.vo.Reports;

import java.util.List;

public interface UsersReportDao {
    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    int insertSelective(UsersReport record);

    UsersReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersReport record);

    int updateByPrimaryKey(UsersReport record);

    List<Reports> selectAllVideoReport();
}