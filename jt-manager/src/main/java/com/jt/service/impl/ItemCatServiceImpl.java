package com.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.selfAnnotation.CacheFind;
import com.jt.service.ItemCatService;
import com.jt.utils.ObejctMapperUtils;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    //当容器启动时，新生成的ItemCatServiceImpl类不用直接注入，用的时候再注入，不false注入失败程序会启动不了;
    @Autowired(required = false)
    private Jedis jedis;
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public ItemCat findItemCatByID(Long itemCatId) {
        return itemCatMapper.selectById(itemCatId);
    }

    /**
     * 页面需要返回vo对象
     * 1.数据中查询的结果为pojo对象
     * 2需要将pojo对象转换为vo对象
     * vo:id text state（open closed）
     * pojo:id name isParent==true?closed:open
     * @param parentId
     * @return
     */

    @Override
    public List<EasyUITree> findItemCatByParentID(Long parentId) {
        List<EasyUITree> treeList =new ArrayList<>();

        //查询数据库记录
      List<ItemCat>  itemCatList=findItemCatListByParentId(parentId);


        for (ItemCat itemCat : itemCatList) {
            Long id=itemCat.getId();
            String text=itemCat.getName();
            String state=itemCat.getIsParent()?"closed":"open";
            EasyUITree uiTree=new EasyUITree(id,text,state);
            treeList.add(uiTree);

        }
        return treeList;
    }

    private List<ItemCat> findItemCatListByParentId(Long parentId) {
        QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_Id",parentId);
     return    itemCatMapper.selectList(queryWrapper);

    }
    @CacheFind()//到时候也会被注入springmvc,当做aop的入口参数直接用就可以了
    @Override
    public List<EasyUITree> findItemCatByCache(Long parentId) {
//       String key="com.jt.service.ItemCatServiceImpl.findItemCatByCache"+parentId;//也可以方法名的全路径
//        String result = jedis.get(key);

        List<EasyUITree>  itemCatByParentID=new ArrayList<>();

//        if(StringUtils.isEmpty(result)){
             itemCatByParentID = findItemCatByParentID(parentId);
//             String json= ObejctMapperUtils.toJson(itemCatByParentID);
//             jedis.setex(key,7*24*3600,json);
//            System.out.println("业务查询数据库");
//        }else{
//            //表示缓存中有数据
//         itemCatByParentID = ObejctMapperUtils.toObject(result, itemCatByParentID.getClass());
//            System.out.println("业务查询redis");
//        }

        return itemCatByParentID;
    }
}
