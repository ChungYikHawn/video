package com.hang.hangvideosdev.service.impl;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.common.idworker.Sid;
import com.hang.hangvideosdev.mapper.UsersDao;
import com.hang.hangvideosdev.mapper.UsersFansDao;
import com.hang.hangvideosdev.mapper.UsersLikeVideosDao;
import com.hang.hangvideosdev.mapper.UsersReportDao;
import com.hang.hangvideosdev.pojo.Users;
import com.hang.hangvideosdev.pojo.UsersFans;
import com.hang.hangvideosdev.pojo.UsersLikeVideos;
import com.hang.hangvideosdev.pojo.UsersReport;
import com.hang.hangvideosdev.pojo.vo.PublisherVideo;
import com.hang.hangvideosdev.pojo.vo.UsersVO;
import com.hang.hangvideosdev.service.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


@Service("userService")
public class UserServiceImpl implements UserService {
    public static final String USER_REDIS_SESSION = "user-redis-session";

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UsersLikeVideosDao usersLikeVideosDao;

    @Autowired
    private UsersFansDao usersFansDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UsersReportDao usersReportDao;

    @Autowired
    private Sid sid;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        int result = usersDao.selectUsername(username);
        return result>0? true:false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(Users user) {
        String id=sid.nextShort();
        user.setId(id);
        usersDao.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {
        return usersDao.selectLogin(username,password);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult uploadFace(String userId, MultipartFile[] files,String FILE_SPACE) {
        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalFacePath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
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

        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        int i = usersDao.updateByPrimaryKeySelective(user);
        if (i>0) return JSONResult.ok(uploadPathDB);
        else return JSONResult.errorMsg("上传出错...");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult queryUserInfo(String userId,String fanId) {
        Users userInfo = usersDao.queryUserInfo(userId);
        if (userInfo!=null) {
            UsersVO userVO = new UsersVO();
            BeanUtils.copyProperties(userInfo, userVO);
            String uniqueToken = String.valueOf(redisTemplate.opsForValue().get(USER_REDIS_SESSION + ":" + userId)).trim();
            userVO.setUserToken(uniqueToken);
            userVO.setFollow(usersFansDao.queryIsFollow(userId,fanId)==1?true:false);
            return JSONResult.ok(userVO);
        }
        return JSONResult.errorMsg("查询失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult queryPublisher(String loginUserId, String videoId, String publishUserId) {
        Users userInfo = usersDao.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo, publisher);
        // 2. 查询当前登录者和视频的点赞关系
        int userLikeVideo = usersLikeVideosDao.isUserLikeVideo(loginUserId, videoId);

        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo==1? true:false);

        return JSONResult.ok(bean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult saveUserFanRelation(String userId, String fanId) {
        int update1=usersDao.addFansCount(userId);
        int update2=usersDao.addFollowersCount(fanId);
        UsersFans usersFans=new UsersFans();
        usersFans.setId(sid.nextShort());
        usersFans.setUserId(userId);
        usersFans.setFanId(fanId);
        int insert = usersFansDao.insert(usersFans);
        if (update1>0&&update2>0&&insert>0) return JSONResult.ok("关注成功");
        return JSONResult.errorMsg("关注失败");
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONResult deleteUserFanRelation(String userId, String fanId) {
        int update1=usersDao.subFansCount(userId);
        int update2=usersDao.subFollowersCount(fanId);
        int delete=usersFansDao.deleteByUAF(userId,fanId);
        if (update1>0&&update2>0&&delete>0) return JSONResult.ok("取消关注成功");
        return JSONResult.errorMsg("取消关注失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONResult reportUser(UsersReport userReport) {

        String urId = sid.nextShort();
        userReport.setId(urId);
        userReport.setCreateDate(new Date());

        int insert = usersReportDao.insert(userReport);
        if(insert>0) return JSONResult.ok("举报已受理，平台有你更美好");
        else return JSONResult.errorMsg("举报失败");
    }
}
