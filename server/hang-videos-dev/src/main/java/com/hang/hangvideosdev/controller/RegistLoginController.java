package com.hang.hangvideosdev.controller;

import java.util.UUID;

import com.hang.hangvideosdev.common.JSONResult;
import com.hang.hangvideosdev.common.MD5Utils;
import com.hang.hangvideosdev.pojo.Users;
import com.hang.hangvideosdev.pojo.vo.UsersVO;
import com.hang.hangvideosdev.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="用户注册登录的接口", tags= {"注册和登录的controller"})
public class RegistLoginController extends BasicController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@ApiOperation(value="用户注册", notes="用户注册的接口")
	@PostMapping("/regist")
	public JSONResult regist(@RequestBody Users user) throws Exception {
		//1.检验传入参数是否为空
		if (StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
			return JSONResult.errorMsg("用户名和密码不能为空");
		}
		//2.判断用户名是否存在
		boolean usernameIsExist=userService.queryUsernameIsExist(user.getUsername());
		if (!usernameIsExist) {
			user.setNickname(user.getUsername());
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setReceiveLikeCounts(0);
			userService.saveUser(user);
			return JSONResult.ok();
		}else {
			return JSONResult.errorMsg("用户名已存在");
		}
	}


	public UsersVO setUserRedisSessionToken(Users userModel) {
		String uniqueToken = UUID.randomUUID().toString();
//		redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		redisTemplate.opsForValue().set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);
		UsersVO userVO = new UsersVO();
		BeanUtils.copyProperties(userModel, userVO);
		userVO.setUserToken(uniqueToken);
		return userVO;
	}
    @ApiOperation(value="用户登录", notes="用户登录的接口")
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

//		Thread.sleep(3000);

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResult.ok("用户名或密码不能为空...");
        }
		//2.判断用户名是否存在
		boolean usernameIsExist=userService.queryUsernameIsExist(user.getUsername());
        if (!usernameIsExist) return JSONResult.errorMsg("用户名不存在");


        // 3. 判断用户是否存在
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(user.getPassword()));

        // 4. 返回
        if (userResult != null) {
            userResult.setPassword("");
            	UsersVO userVO = setUserRedisSessionToken(userResult);
            return JSONResult.ok(userVO);
        } else {
            return JSONResult.errorMsg("密码不正确, 请重试...");
        }
    }

	@ApiOperation(value="用户注销", notes="用户注销的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
			dataType="String", paramType="query")
	@PostMapping("/logout")
	public JSONResult logout(String userId) throws Exception {
		redisTemplate.delete(USER_REDIS_SESSION + ":" + userId);
		return JSONResult.ok();
	}
}
