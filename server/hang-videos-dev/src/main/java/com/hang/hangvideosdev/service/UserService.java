package com.hang.hangvideosdev.service;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.pojo.Users;
import com.hang.hangvideosdev.pojo.UsersReport;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    boolean queryUsernameIsExist(String username);
    void saveUser(Users user);

    Users queryUserForLogin(String username, String password);

    JSONResult uploadFace(String userId, MultipartFile[] files,String FILE_SPACE);

    JSONResult queryUserInfo(String userId,String fanId);

    JSONResult queryPublisher(String loginUserId, String videoId, String publishUserId);

    JSONResult saveUserFanRelation(String userId, String fanId);

    JSONResult deleteUserFanRelation(String userId, String fanId);

    JSONResult reportUser(UsersReport usersReport);
}
