package com.re.session11.repository;

import com.re.session11.entity.ShoppingCart;

import java.util.Optional;

public interface CartRepository {
    Optional<ShoppingCart> findByUserId(Long userId);
    ShoppingCart save(ShoppingCart cart);
}
