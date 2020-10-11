package com.itheima.health.vo;


import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RoleVO extends Role {

    private Set<Permission> permissions = new HashSet<Permission>(0);//对应权限集合
    private List<MenuVO> menuVOList = new ArrayList<>();
}
