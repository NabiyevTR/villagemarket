package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.mapper.CartMapper;
import ru.ntr.villagemarket.model.repository.CartRepository;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;



    @Override
    public CartDto get() {
        return cartMapper.fromCart(cartRepository.getById(getCartId()));
    }

    @Override
    public void overwrite(CartDto cartDto) {
        cartRepository.deleteById(getCartId());
        cartRepository.save(cartMapper.toCart(getCartId(), cartDto));
    }

    @Override
    public void checkout() {
        cartRepository.deleteById(getCartId());
    }

    @Override
    public void clearCart() {
        cartRepository.deleteById(getCartId());
    }



    private int getCartId() {
        return ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser()
                .getCart()
                .getId();
    }


}
