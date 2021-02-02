package com.hang.hangvideosdev.mapper;

import com.hang.hangvideosdev.pojo.Bgm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BgmDao {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);

    List<Bgm> list();

    Bgm queryBgmById(String bgmId);
}