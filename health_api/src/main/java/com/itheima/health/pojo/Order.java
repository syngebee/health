package com.itheima.health.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检预约信息
 */
@Data
@TableName(value = "t_order")
public class Order implements Serializable{
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "MEMBER_ID")
    private Integer memberId;//会员id
    @TableField(value = "ORDERDATE")
    private Date orderDate;//预约日期
    @TableField(value = "ORDERTYPE")
    private String orderType;//预约类型 电话预约/微信预约
    @TableField(value = "ORDERSTATUS")
    private String orderStatus;//预约状态（是否到诊）  未到诊   已到诊
    @TableField(value = "SETMEAL_ID")
    private Integer setmealId;//体检套餐id



}
