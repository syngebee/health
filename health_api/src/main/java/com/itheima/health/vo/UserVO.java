package com.itheima.health.vo;


import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.pojo.User;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserVO extends User {

    private Set<RoleVO> roles = new HashSet<RoleVO>(0);//对应角色集合

}
