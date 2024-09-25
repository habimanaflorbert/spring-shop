package com.jambo.dream_shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jambo.dream_shop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
