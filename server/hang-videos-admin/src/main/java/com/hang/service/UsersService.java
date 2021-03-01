package com.hang.service;


import com.hang.common.utils.PagedResult;
import com.hang.pojo.Users;

public interface UsersService {

    PagedResult queryUsers(Users user, Integer page, Integer pageSize);
}
