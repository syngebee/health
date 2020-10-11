package com.itheima.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.health.pojo.User;
import com.itheima.health.vo.UserVO;

public interface UserService extends IService<User> {
    UserVO findUserDetailsInfoByUsername(String username);
}
