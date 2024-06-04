package com.sweet.simple.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author: [ph]
 * @Date: 2024/6/1 17:05
 * @Description: 邮件表
 **/
@Setter
@Getter
@Builder
@TableName("taiwan_cain_2nd.postal")
public class Postal {

    /**
     * 主键
     */
    @TableId(value = "postal_id", type = IdType.AUTO)
    private Integer postalId;

    /**
     * 产生时间
     */
    private LocalDateTime occTime;

    /**
     * 发送人编号
     */
    private Integer sendCharacNo;

    /**
     * 发送人名称
     */
    private String sendCharacName;

    /**
     * 接收角色编码
     */
    private Integer receiveCharacNo;

    /**
     * 物品id，指代code
     */
    private Integer itemId;

    /**
     * 数量
     */
    private Integer addInfo;

    /**
     * 耐久度
     */
    private Integer endurance;

    /**
     * 强化等级
     */
    private Integer upgrade;

    /**
     * 增幅项
     *
     * <p>
     *
     * @ see AmplifyOptionEnum
     * 1：体力
     * 2：精神
     * 3：力量
     * 4：智力
     */
    private Integer amplifyOption;

    /**
     * 附加值，有什么作用暂时不知道
     */
    private Integer amplifyValue;

    /**
     * 金币数量
     */
    private Integer gold;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 删除标识（0：否；1：是）
     */
    private Integer deleteFlag;

    /**
     * 时装标识（0：否；1：是）
     */
    private Integer avataFlag;

    /**
     * 是否限制使用天数（0：否；1：是）
     */
    private Integer unlimitFlag;

    /**
     * 封印标识（0：否；1：是）
     */
    private Integer sealFlag;

    /**
     * 宠物/宠物蛋标识（（0：否；1：是））
     */
    private Integer creatureFlag;

    /**
     * 邮箱 TODO
     */
    private Integer postal;

    /**
     * 最后的信件id
     */
    private Integer letterId;

    /**
     * 未知
     */
    private Integer extendInfo;

    /**
     * 未知
     */
    private Integer ipgDbId;

    /**
     * 未知
     */
    private Integer ipgTransactionId;

    /**
     * 未知
     */
    private String ipgNexonId;

    /**
     * 拍卖行id
     */
    private Long auctionId;

    /**
     * 未知
     */
    private byte[] randomOption;

    /**
     * 锻造等级
     */
    private Integer seperateUpgrade;

    /**
     * 类型 TODO
     */
    private Integer type;

    /**
     * 未知
     */
    private byte[] itemGuid;
}
