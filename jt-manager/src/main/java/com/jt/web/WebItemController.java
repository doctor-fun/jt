package com.jt.web;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/item")
//用于web应用专门远程访问的uri
//https://tva1.sinaimg.cn/large/006tNbRwly1gb5t7bxaoyj31jl0u0wyl.jpg
public class WebItemController {
    @Autowired
    private ItemService itemService;

    /**
     * http://manager.jt.com/web/item.../findItemById?id=xxx
     */
    @RequestMapping("/findItemById/{itemId}")
    public Item findItemById(@PathVariable Long itemId){
     return    itemService.findItemById(itemId);

    }

    @RequestMapping("/findItemDescById/{itemId}")
    public ItemDesc findItemDescById(@PathVariable Long itemId){
        return itemService.findItemDescByItemId(itemId);
    }
}
