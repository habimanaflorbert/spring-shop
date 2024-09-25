package com.jambo.dream_shop.service.cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jambo.dream_shop.exceptions.ResourceNotFoundException;
import com.jambo.dream_shop.model.Cart;
import com.jambo.dream_shop.model.CartItem;
import com.jambo.dream_shop.repository.CartItemRepository;
import com.jambo.dream_shop.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount=cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }
    
    @Override
    public void clearCart(Long id) {
        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart=getCart(id);
        return cart.getTotalAmount();
    }
    
}
