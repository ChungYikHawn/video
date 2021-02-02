package com.hang.hangvideosdev.controller;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.common.PagedResult;
import com.hang.hangvideosdev.pojo.Bgm;
import com.hang.hangvideosdev.pojo.Comments;
import com.hang.hangvideosdev.pojo.Videos;
import com.hang.hangvideosdev.service.BgmService;
import com.hang.hangvideosdev.service.VideoService;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(value="视频相关业务的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value="上传视频", notes="上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=false,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoWidth", value="视频宽度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoHeight", value="视频高度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="desc", value="视频描述", required=false,
                    dataType="String", paramType="form")
    })
    @PostMapping(value="/upload", headers="content-type=multipart/form-data")
    public JSONResult upload(String userId,
                                  String bgmId, double videoSeconds,
                                  int videoWidth, int videoHeight,
                                  String desc,
                                  @ApiParam(value="短视频", required=true)
                                          MultipartFile file) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        return videoService.upload(userId,bgmId,videoSeconds,videoWidth,videoHeight,desc,file,FILE_SPACE,FFMPEG_EXE);
    }


    @PostMapping(value="/showAll")
    public JSONResult showAll(@RequestBody Videos video, Integer isSaveRecord,
                              @RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value="pageSize",defaultValue = "5") Integer pageSize) throws Exception {
        return videoService.getAllVideos(video, isSaveRecord, page, pageSize);
    }

    @PostMapping(value="/showMyLike")
    public JSONResult showMyLike(String userId,
                              @RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value="pageSize",defaultValue = "6") Integer pageSize) throws Exception {
        return videoService.queryMyLikeVideos(userId, page, pageSize);
    }

    @PostMapping(value="/showMyFollow")
    public JSONResult showMyFollow(String userId,
                              @RequestParam(value = "page",defaultValue = "1") Integer page,
                              @RequestParam(value="pageSize",defaultValue = "6") Integer pageSize) throws Exception {
        return videoService.queryMyFollowVideos(userId, page, pageSize);
    }


    @PostMapping(value="/hot")
    public JSONResult hot() throws Exception {
        return videoService.getHotWords();
    }

    @PostMapping(value="/userLike")
    public JSONResult userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        return videoService.userLikeVideo(userId, videoId, videoCreaterId);
    }

    @PostMapping(value="/userUnLike")
    public JSONResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        return videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
    }


    @PostMapping("/saveComment")
    public JSONResult saveComment(@RequestBody Comments comment,
                                       String fatherCommentId, String toUserId) throws Exception {

        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);
        return videoService.saveComment(comment);
    }

    @PostMapping("/getVideoComments")
    public JSONResult getVideoComments(String videoId,
                                       @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) throws Exception {

        if (StringUtils.isBlank(videoId)) {
            return JSONResult.ok();
        }
        return videoService.getAllComments(videoId, page, pageSize);

    }
}
