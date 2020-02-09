package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements DubboOrderService {
    @Autowired
    private OrderMapper orderMapper;

   @Autowired
   private OrderShippingMapper orderShippingMapper;
   @Autowired
   private OrderItemMapper orderItemMapper;


   @Transactional
    @Override
    public String saveOrder(Order order) {
       //要主动生成orderid ，base
       // userid和
       // 时间

       String orderId =""+order.getUserId()+System.currentTimeMillis()+"";
       //订单入库

       Date date=new Date();
        order.setOrderId(orderId)
        .setStatus(1)
        .setCreated(date)
        .setUpdated(date);
        orderMapper.insert(order);
       System.out.println("订单入库成功");


       //为入库的订单添加物流信息
       OrderShipping shipping=order.getOrderShipping();
       shipping.setOrderId(orderId).setCreated(date).setUpdated(date);
       orderShippingMapper.insert(shipping);
       System.out.println("订单物流入库成功");
       
       
       //订单商品入库
       List<OrderItem> orderItems = order.getOrderItems();
       for (OrderItem orderItem : orderItems) {

           orderItem.setOrderId(orderId).setCreated(date).setUpdated(date);
           orderItemMapper.insert(orderItem);
       }
       System.out.println("订单入库全部成功");

       return orderId;
    }

    @Override
    public Order findOrderById(String id) {
        return null;
    }
}
