package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {



    public Item findItemById(int itemId) ;

    ItemDesc findItemDescById(int itemId);
}
