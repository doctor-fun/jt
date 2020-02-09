package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITree {
    private Long id; //节点的Id信息
    private String text; //节点名称
    private String state;//节点状态 open，closed,就是一级菜单是否显示二级菜单


}
