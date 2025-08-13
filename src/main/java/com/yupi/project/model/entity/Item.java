package com.yupi.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 二次元IP周边商品购买信息表（购买时间精确到日）
 * @TableName item
 */
@TableName(value ="item")
@Data
public class Item {
    /**
     * 自增主键，唯一标识每条记录
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * item名字（如周边商品名称）
     */
    @TableField(value = "itemName")
    private String itemName;

    /**
     * item对应的二次元IP名称（如动漫、游戏、影视作品名）
     */
    @TableField(value = "itemIp")
    private String itemIp;

    /**
     * item种类（如徽章、手办、立牌等）
     */
    @TableField(value = "itemCategory")
    private String itemCategory;

    /**
     * 单价（保留2位小数）
     */
    @TableField(value = "unitPrice")
    private BigDecimal unitPrice;

    /**
     * 总价（保留2位小数）
     */
    @TableField(value = "totalPrice")
    private BigDecimal totalPrice;

    /**
     * 购买数量（非负整数）
     */
    @TableField(value = "purchaseNumber")
    private Integer purchaseNumber;

    /**
     * 购买时间（仅到日，格式：YYYY-MM-DD）
     */
    @TableField(value = "purchaseTime")
    private Date purchaseTime;
}