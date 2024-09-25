package com.jambo.dream_shop.service.cart;

import java.math.BigDecimal;

import com.jambo.dream_shop.model.Cart;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    

}
