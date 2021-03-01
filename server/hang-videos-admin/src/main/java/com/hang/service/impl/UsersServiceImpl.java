package com.hang.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hang.common.utils.PagedResult;
import com.hang.mapper.UsersDao;
import com.hang.pojo.Users;
import com.hang.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public PagedResult queryUsers(Users user, Integer page, Integer pageSize) {
        String username=null;
        String nickname=null;
        if (user.getUsername()!=null){
            username=new StringBuilder().append("%").append(user.getUsername()).append("%").toString() ;
        }
        if(user.getNickname()!=null){
            nickname=new StringBuilder().append("%").append(user.getNickname()).append("%").toString() ;
        }

        PageHelper.startPage(page, pageSize);
        List<Users> usersList=usersDao.usersList(username,nickname);
        PageInfo<Users> pageList = new PageInfo<Users>(usersList);
        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(usersList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
