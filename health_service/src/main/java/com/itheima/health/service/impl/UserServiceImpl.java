package com.itheima.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.health.mapper.PermissionMapper;
import com.itheima.health.mapper.RoleMapper;
import com.itheima.health.mapper.UserMapper;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import com.itheima.health.vo.RoleVO;
import com.itheima.health.vo.UserVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public UserVO findUserDetailsInfoByUsername(String username) {
        UserVO userVO = new UserVO();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = getOne(queryWrapper);

        if (user==null || user.getId()==null){
            return userVO;
        }

        BeanUtils.copyProperties(user,userVO);
        int[] roleIds = roleMapper.findRoleIdsByUserId(user.getId());
        Set<RoleVO> roles = new HashSet<>();

        for (int roleId : roleIds) {
            Role role = roleMapper.selectById(roleId);
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role,roleVO);
            roles.add(roleVO);

            //查询下方roleVO需要的permissions
            Set<Permission> permissions = new HashSet<>();
            int[] permissionsIds = roleMapper.findPermissionsIdsByRoleId(role.getId());
            for (int permissionsId : permissionsIds) {
                Permission permission = permissionMapper.selectById(permissionsId);
                permissions.add(permission);
            }
            roleVO.setPermissions(permissions);
        }
        userVO.setRoles(roles);
        return userVO;
    }
}
