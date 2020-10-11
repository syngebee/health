package com.itheima.health.security_service;


import com.itheima.health.pojo.Permission;
import com.itheima.health.service.UserService;
import com.itheima.health.vo.RoleVO;
import com.itheima.health.vo.UserVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class HealthUserServiceDetailsImpl implements UserDetailsService {

    @Reference
     private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         //   根据用户账号  查询多表数据  users   roles   permisson数据
         UserVO user =  userService.findUserDetailsInfoByUsername(username);
        //  user   账号  密码  角色关键字 和 权限关键字
         List<GrantedAuthority> userList = new ArrayList<GrantedAuthority>();
        Set<RoleVO> roles = user.getRoles();
        for (RoleVO role : roles) {
            userList.add(new SimpleGrantedAuthority(role.getKeyword()));
             //   获取每一个角色 对应 权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                userList.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }

        }
         //   数据库查询真实用户信息和权限信息 封装到  security 框架  UserDetails对象中
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),userList);

    }




}
