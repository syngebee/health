package com.itheima.health.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单
 */
@Data
@TableName(value = "t_menu")
public class Menu implements Serializable{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name; // 菜单名称
    @TableField(value = "LINKURL")
    private String linkUrl; // 访问路径
    private String path;//菜单项所对应的路由路径
    private Integer priority; // 优先级（用于排序）
    private String description; // 描述
    private String icon;//图标
    @TableField(value = "PARENTMENUID")
    private Integer parentMenuId;//父菜单id


}
