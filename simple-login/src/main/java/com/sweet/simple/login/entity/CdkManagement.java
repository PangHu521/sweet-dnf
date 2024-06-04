package com.sweet.simple.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: [ph]
 * @Date: 2024/6/1 17:17
 * @Description: cdk表
 **/
@Getter
@Setter
@Builder
@TableName("taiwan_cain.cdk_management")
public class CdkManagement {

    /**
     * 主键id
     */
    private Integer cdkId;

    /**
     * cdk兑换码(兑换界面使用的)
     */
    private String cdkCode;

    /**
     * cdk名称
     */
    private String cdkName;

    /**
     * 对应的物品编号
     */
    private Integer itemId;

    /**
     * 物品数量
     */
    private Integer itemCount;

    /**
     * 兑换角色编号
     */
    private Integer characNo;

    /**
     * 兑换角色名字
     */
    private String characName;

    /**
     * cdk生成日期
     */
    private Date makeDate;

    /**
     * cdk有效时间(1天，3天，7天....)
     */
    private Integer validTime;

    /**
     * 是否使用标识(0未使用，1已使用)
     */
    private Integer useFlag;

    /**
     * cdk被使用的日期
     */
    private Date useDate;
}