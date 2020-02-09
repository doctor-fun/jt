package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品描述表,简要概要
 *
 */
@TableName("tb_item_desc")
@Data
@Accessors(chain = true)
public class ItemDesc extends BasePojo{
    @TableId//不能主键自增，这个是关联外键，由另一张表拿到
    private Long itemId;//商品表中的id与desc的id一致
    private String itemDesc;//详情信息


}
