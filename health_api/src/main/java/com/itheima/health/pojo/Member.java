package com.itheima.health.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员
 */
@Data
@TableName(value = "t_member")
public class Member implements Serializable{
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//主键
    @TableField(value = "FILENUMBER")
    private String fileNumber;//档案号
    private String name;//姓名
    private String sex;//性别
    @TableField(value = "IDCARD")
    private String idCard;//身份证号
    @TableField(value = "PHONENUMBER")
    private String phoneNumber;//手机号
    @TableField(value = "REGTIME")
    private Date regTime;//注册时间
    private String password;//登录密码
    private String email;//邮箱
    private Date birthday;//出生日期
    private String remark;//备注


}
