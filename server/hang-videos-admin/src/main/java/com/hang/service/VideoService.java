package com.hang.service;


import com.hang.common.utils.JSONResult;
import com.hang.common.utils.PagedResult;
import com.hang.pojo.Bgm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {
    PagedResult queryReportList(Integer page, Integer pageSize);

    JSONResult updateVideoStatus(String videoId, Integer value);

    PagedResult queryBgmList(Integer page, Integer i);

    int addBgm(Bgm bgm);

    JSONResult deleteBgm(String bgmId);

    JSONResult bgmUpload(MultipartFile[] files) throws IOException, Exception;
}
