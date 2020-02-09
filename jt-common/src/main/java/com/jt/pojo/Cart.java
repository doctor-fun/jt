package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
* @author fangchen
* @date 2020/2/7 2:56 下午
 * 购物车
*/
@TableName("tb_cart")
@Data
@Accessors(chain=true)
public class Cart extends BasePojo {

    @TableId(type= IdType.AUTO)
    private Long id;

    ///外键
    private Long userId;
    private Long itemId;


    private String itemTitle;
    private String itemImage;
    private Long itemPrice;
    //购物车商品数目
    private Integer num;

}
