package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {
    ItemCat findItemCatByID(Long itemCatId);

    List<EasyUITree> findItemCatByParentID(Long parentId);

    List<EasyUITree> findItemCatByCache(Long parentId);
}
