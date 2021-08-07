package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.cart.CartDto;
import ru.ntr.villagemarket.model.entity.Cart;

public interface CartService {

    CartDto getCartDto();

    Cart getCart();

    void update(CartDto cartDto);

    void checkout();

    void clearCart();
}
