package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.entity.Cart;

public interface CartService {

    CartDto getCartDto();

    Cart getCart();

    void overwrite(CartDto cartDto);

    void checkout();

    void clearCart();
}
