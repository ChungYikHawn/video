package com.hang.mapper;

import com.hang.pojo.Bgm;

import java.util.List;

public interface BgmDao {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);

    List<Bgm> selectAll();
}