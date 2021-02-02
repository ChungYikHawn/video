package com.hang.hangvideosdev.service;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.common.PagedResult;
import com.hang.hangvideosdev.pojo.Comments;
import com.hang.hangvideosdev.pojo.Videos;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    JSONResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String desc, MultipartFile file, String fileSpace,String FFMPEG_EXE) throws Exception;

    JSONResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    JSONResult getHotWords();

    JSONResult userLikeVideo(String userId, String videoId, String videoCreaterId);

    JSONResult userUnLikeVideo(String userId, String videoId, String videoCreaterId);


    JSONResult saveComment(Comments comment);

    JSONResult getAllComments(String videoId, Integer page, Integer pageSize);

    JSONResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    JSONResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);
}
