package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.entity.Cart;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.mapper.OrderProductsMapper;
import ru.ntr.villagemarket.model.repository.CartRepository;
import ru.ntr.villagemarket.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final OrderProductsMapper orderProductsMapper;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @Override
    public CartDto getCartDto() {
        return orderProductsMapper.fromCart(cartRepository.getById(getCartId()));
    }

    @Override
    public Cart getCart() {
        return cartRepository.getById(getCartId());
    }

    @Override
    public void overwrite(CartDto cartDto) {
        cartRepository.save(orderProductsMapper.toCart(getCartId(), cartDto));

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

        User currentUser = userService.getCurrentUser();

        Cart cart = currentUser.getCart();

        //create new cart if cart is null
        if (cart == null) {
            cart = cartRepository.save(
                    Cart.builder()
                            .id(currentUser.getId())
                            .build());
        }
        return cart.getId();
    }
}
