package com.jt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.service.DubboOrderService;
import com.jt.utils.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
/**
* @author fangchen
* @date 2020/2/9 12:56 下午
*/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(check=false)
    private DubboOrderService orderService;

    @Reference
    private DubboCartService cartService;
    /**
     * 跳转到订单的的确认页
     *creat.html
     */
    @RequestMapping("/create")
    public String create(Model model){
        User user = UserThreadLocal.getUser();

        List<Cart>  cartList=cartService.findCartListByUserId(user.getId());
        model.addAttribute("cart",cartList);


        return "order-cart";
    }
    @RequestMapping("/submit")
    public SysResult saveOrder(Order order){
        Long userId = UserThreadLocal.getUser().getId();
        order.setUserId(userId);
        String orderId = orderService.saveOrder(order);
        return SysResult.ok(orderId);

    }

}
