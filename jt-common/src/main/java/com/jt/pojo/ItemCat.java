package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat {
    //商品分类分几类
    //一级标题
    //二级标题
    //三级标题
    @TableId(type= IdType.AUTO)
    private  Long id;
    private  Long parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;//排序号
    private Boolean isParent;//是否为父级

}
