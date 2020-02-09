package com.jt.service.Impl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.utils.ObejctMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private HttpClientService httpClientService;

    @Override
    public Item findItemById(int itemId) {
        String url="http://manage.jt.com/web/item/findItemById/"+itemId;
        //为了满足get请求需求定义 id =xxx
//        Map<String ,String > params=new HashMap<>();
//        params.put("id",itemId+"");
        String response = httpClientService.doGet(url);
        Item item = ObejctMapperUtils.toObject(response, Item.class);
        return item;
    }

    @Override
    public ItemDesc findItemDescById(int itemId) {
        String url="http://manage.jt.com/web/item/findItemDescById/"+itemId;
        String response = httpClientService.doGet(url);
        ItemDesc itemDesc=ObejctMapperUtils.toObject(response,ItemDesc.class);
        return itemDesc;
    }
}
