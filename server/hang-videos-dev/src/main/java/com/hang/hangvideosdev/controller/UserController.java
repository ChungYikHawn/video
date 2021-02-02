package com.hang.hangvideosdev.controller;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.pojo.Users;
import com.hang.hangvideosdev.pojo.UsersReport;
import com.hang.hangvideosdev.pojo.vo.UsersVO;
import com.hang.hangvideosdev.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
@RestController
@Api(value="用户相关业务的接口", tags= {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController{

    @Autowired
    private UserService userService;

    @ApiOperation(value="用户上传头像", notes="用户上传头像的接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(String userId,
                                 @RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }
        System.out.println(userId);
        System.out.println(files);
        return userService.uploadFace(userId,files,FILE_SPACE);
    }

    @ApiOperation(value="查询用户信息", notes="查询用户信息的接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("/query")
    public JSONResult query(String userId,String fanId) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }
        return userService.queryUserInfo(userId,fanId);
    }

    @PostMapping("/queryPublisher")
    public JSONResult queryPublisher(String loginUserId, String videoId,
                                          String publishUserId) throws Exception {

        if (StringUtils.isBlank(publishUserId)||StringUtils.isBlank(loginUserId)||StringUtils.isBlank(videoId)){
            return JSONResult.errorMsg("参数错误");
        }
        return userService.queryPublisher(loginUserId,videoId,publishUserId);
    }

    @PostMapping("/beyourfans")
    public JSONResult beyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("");
        }

        return userService.saveUserFanRelation(userId, fanId);

    }

    @PostMapping("/dontbeyourfans")
    public JSONResult dontbeyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("");
        }

        return userService.deleteUserFanRelation(userId, fanId);
    }

    @PostMapping("/reportUser")
    public JSONResult reportUser(@RequestBody UsersReport usersReport) throws Exception {

        return userService.reportUser(usersReport);
    }
}
