package com.jambo.dream_shop.service.cart;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuanity(Long cartId,Long productId,int quantity);

}
