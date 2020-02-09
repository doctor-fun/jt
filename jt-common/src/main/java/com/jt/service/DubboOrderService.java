package com.jt.service;

import com.jt.pojo.Order;

public interface DubboOrderService {

    /**
     * 保存用户的某一时间的订单信息
     * @param order
     * @return
     */
    String saveOrder(Order order);

    Order findOrderById(String id);
}
