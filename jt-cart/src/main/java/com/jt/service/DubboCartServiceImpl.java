package com.jt.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import lombok.experimental.Accessors;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
/**
* @author fangchen
* @date 2020/2/7 12:44 下午
*/
@Service
public class DubboCartServiceImpl implements DubboCartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return cartMapper.selectList(queryWrapper);
    }

    @Override
    public void updateCartNum(Cart cart){
        Cart cartTemp=new Cart();
        cartTemp.setNum(cart.getNum()).setUpdated(new Date());
        UpdateWrapper<Cart> updateCartWrapper=new UpdateWrapper<>();
        updateCartWrapper.eq("user_id",cart.getUserId()).eq("item_id",cart.getItemId());


         cartMapper.update(cartTemp,updateCartWrapper);
    }

    @Override
    public void saveCart(Cart cart) {
        //存在就更新
        //不存在就新增

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cart.getUserId()).eq("item_id",cart.getItemId());
        Cart cartTemp = cartMapper.selectOne(queryWrapper);
        if(cartTemp== null){
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);

        }else {
            int num =cart.getNum()+cartTemp.getNum();
            cartTemp.setNum(num).setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }

    }
}
