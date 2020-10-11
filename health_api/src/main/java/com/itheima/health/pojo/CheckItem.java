package com.itheima.health.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 检查项
 */
@TableName(value = "t_checkitem")
@Data
public class CheckItem implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//主键
    private String code;//项目编码
    private String name;//项目名称
    private String sex;//适用性别
    private String age;//适用年龄（范围），例如：20-50
    private Float price;//价格
    private String type;//检查项类型，分为检查和检验两种类型
    private String remark;//项目说明
    private String attention;//注意事项
    private Integer is_delete=0;// 是否删除  1 表示删除  0 表示未删除  添加默认未删除


}
