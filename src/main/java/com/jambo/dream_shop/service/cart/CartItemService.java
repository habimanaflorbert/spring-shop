package com.jambo.dream_shop.service.cart;

import org.springframework.stereotype.Service;

import com.jambo.dream_shop.model.Cart;
import com.jambo.dream_shop.model.CartItem;
import com.jambo.dream_shop.model.Product;
import com.jambo.dream_shop.repository.CartItemRepository;
import com.jambo.dream_shop.repository.CartRepository;
import com.jambo.dream_shop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getItems().stream()
        .filter(item->item.getProduct().getId().equals(productId))
        .findFirst().orElse(new CartItem());
        if (cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantiy(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantiy(cartItem.getQuantiy() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        
        
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeItemFromCart'");
    }

    @Override
    public void updateItemQuanity(Long cartId, Long productId, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateItemQuanity'");
    }

}
