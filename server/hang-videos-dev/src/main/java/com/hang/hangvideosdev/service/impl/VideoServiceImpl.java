package com.hang.hangvideosdev.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hang.hangvideosdev.common.*;
import com.hang.hangvideosdev.common.enums.VideoStatusEnum;
import com.hang.hangvideosdev.common.idworker.Sid;
import com.hang.hangvideosdev.mapper.*;
import com.hang.hangvideosdev.pojo.*;
import com.hang.hangvideosdev.pojo.vo.CommentsVO;
import com.hang.hangvideosdev.pojo.vo.VideosVO;
import com.hang.hangvideosdev.service.BgmService;
import com.hang.hangvideosdev.service.VideoService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Autowired
    private BgmDao bgmDao;

    @Autowired
    private VideosDao videoDao;

    @Autowired
    private SearchRecordsDao searchRecordsDao;

    @Autowired
    private UsersLikeVideosDao usersLikeVideosDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private CommentsDao commentsDao;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult upload(String userId,
                             String bgmId,
                             double videoSeconds,
                             int videoWidth,
                             int videoHeight,
                             String desc,
                             MultipartFile file,
                             String fileSpace,
                             String FFMPEG_EXE) throws Exception {
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件上传的最终保存路径
        String finalVideoPath = "";
        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                // abc.mp4
                String arrayFilenameItem[] =  fileName.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0 ; i < arrayFilenameItem.length-1 ; i ++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }
                // fix bug: 解决小程序端OK，PC端不OK的bug，原因：PC端和小程序端对临时视频的命名不同
//				String fileNamePrefix = fileName.split("\\.")[0];

                if (StringUtils.isNotBlank(fileName)) {

                    finalVideoPath = fileSpace + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmDao.queryBgmById(bgmId);
            String mp3InputPath = fileSpace + bgm.getPath();
            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/" + userId + "/video" + "/" + videoOutputName;
            finalVideoPath = fileSpace + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
        }
//        System.out.println("uploadPathDB=" + uploadPathDB);
//        System.out.println("finalVideoPath=" + finalVideoPath);

        // 对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.getCover(finalVideoPath, fileSpace + coverPathDB);
        Videos video = new Videos();
        String id=sid.nextShort();
        System.out.println(id);
        video.setId(id);
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds(videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        int count=videoDao.insertSelective(video);
        if (count>0) {
            return JSONResult.ok("videoId");
        }else{
            return JSONResult.errorMsg("上传出错...");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsDao.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videoDao.queryAllVideos(desc, userId);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return JSONResult.ok(pagedResult);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult getHotWords() {
        List<String> list=searchRecordsDao.getHotWords();
        return JSONResult.ok(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult userLikeVideo(String userId, String videoId, String videoCreaterId) {
        String likeId = sid.nextShort();
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        int count = usersLikeVideosDao.insert(ulv);
        int count1 = videoDao.addVideoLikeCount(videoId);
        int count2 = usersDao.addReceiveLikeCount(videoCreaterId);
        if (count>0&&count1>0&&count2>0){
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("点赞失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        int count = usersLikeVideosDao.deleteByUserIdAndVideoId(userId, videoId);
        int count1=videoDao.subVideoLikeCount(videoId);
        int count2=usersDao.subReceiveLikeCount(videoCreaterId);
        if (count>0&&count1>0&&count2>0){
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("取消点赞失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentsDao.insert(comment);
        return JSONResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVO> list =videoDao.queryMyLikeVideos(userId);
        PageInfo<VideosVO> pageList=new PageInfo<>(list);
        PagedResult result =new PagedResult();
        result.setPage(page);
        result.setRows(list);
        result.setTotal(pageList.getPages());
        result.setRecords(pageList.getTotal());
        return JSONResult.ok(result);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public JSONResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videoDao.queryMyFollowVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        return JSONResult.ok(pagedResult);
    }

    @Override
    public JSONResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentsDao.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return JSONResult.ok(grid);
    }
}
