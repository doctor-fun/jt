package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.utils.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.font.CharToGlyphMapper;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Reference
    private DubboCartService cartService;



    /**
     * 跳转购物车
     * http://www.jt.com/cart/show.html
     * 页面数据要求:items="${cartList}"
     *
     */
    @RequestMapping("/show")
    public String  show(Model model){
        Long userId = UserThreadLocal.getUser().getId();
//       User jt_user = (User)model.getAttribute("JT_USER");

//        Long userId=jt_user.getId();
        List<Cart> cartList=cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);

        return "cart";


    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    //如果上面的itemId和num和cart的某个属性名一致，那么可以用cart接收它
    public SysResult updateCartNum(Cart cart){
        //假设一个用户id
        Long userId=7L;
        cart.setUserId(userId);


        cartService.updateCartNum(cart);
        return SysResult.ok();
    }


    /**
     * 购物车产品的加购行为
     */
    @PostMapping("/add/{itemId}")
    public String saveCart(Cart cart){
        Long userId = UserThreadLocal.getUser().getId();
        cart.setUserId(userId);


        cartService.saveCart(cart);
        return "redirect:/cart/show.html";
    }



}
