package com.itheima.health.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class MenuVO extends Menu {

    private List<MenuVO> children = new ArrayList<MenuVO>();//子菜单集合

}
