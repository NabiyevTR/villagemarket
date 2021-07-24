package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.CartDto;

public interface CartService {

    CartDto get();

    void overwrite(CartDto cartDto);

    void checkout();

    void clearCart();
}
