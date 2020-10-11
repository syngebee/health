package com.itheima.health.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组
 */
@Data
@TableName(value = "t_checkgroup")
public class CheckGroup implements Serializable {
  @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//主键
    private String code;//编码
    private String name;//名称
    @TableField(value = "HELPCODE") //  数据库字段是多个英文 查询时 会自动使用下划线拼接  需要将数据库对于的字段映射到实体类属性名上
    private String helpCode;//助记
    private String sex;//适用性别
    private String remark;//介绍
    private String attention;//注意事项
    private Integer is_delete=0;// 是否删除  1 表示删除  0 表示未删除  添加默认未删除
//    @TableField(exist = false) //  数据库中没有该属性对于的字段  但是实体类由于业务需要 需要集合对象
//    private List<CheckItem> checkItems;//一个检查组合包含多个检查项


}
