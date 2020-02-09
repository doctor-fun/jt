package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

public interface ItemService {
    /**
     * 查某一页的数据
     * @param page 第几页
     * @param rows 每页写多少数据
     * @return
     */
    EasyUITable findItemByPage(int page, int rows);

    void saveItem(Item item, ItemDesc itemDesc);


    ItemDesc findItemDescByItemId(Long itemId);

    void updateItem(Item item, ItemDesc itemDesc);

    void deleteItems(Long[] ids);

    void updateStatus(Long[] ids, int status);

   Item  findItemById(Long itemId);
}
