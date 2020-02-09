package com.jt.controller;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;


    /**
     * url:"/
     */
    @RequestMapping("/queryItemName")
    public String findItemCatNameById(Long itemCatId){
        ItemCat itemCat=itemCatService.findItemCatByID(itemCatId);
        return itemCat.getName();
    }

    /*
     * url:/item/cat/list
     * 返回值:list<EasyUITree></>
     * @RequestParam spring mvc的注解
     * defaultValue: 默认值 如果不传数据则使用默认值
     * name/value:表示接收的数据
     * required:是否为必传数据
     */

    @RequestMapping("/list")
    public List<EasyUITree>  findItemCateByParentId(@RequestParam(defaultValue = "0",name="id") Long parentId){
        if(parentId==null) {
            parentId = 0L;//一级菜单的父Id是0
        }
     //   return itemCatService.findItemCatByParentID(parentId);

        return  itemCatService.findItemCatByCache(parentId);
    }
}
