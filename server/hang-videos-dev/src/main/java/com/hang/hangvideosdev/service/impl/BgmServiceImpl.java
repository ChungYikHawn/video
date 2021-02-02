package com.hang.hangvideosdev.service.impl;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.mapper.BgmDao;
import com.hang.hangvideosdev.pojo.Bgm;
import com.hang.hangvideosdev.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bgmService")
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmDao bgmDao;

    @Override
    public JSONResult queryBgmList() {
        return JSONResult.ok(bgmDao.list());
    }
}
