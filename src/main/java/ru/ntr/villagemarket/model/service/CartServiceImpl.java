package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.entity.Cart;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.mapper.CartMapper;
import ru.ntr.villagemarket.model.repository.CartRepository;
import ru.ntr.villagemarket.model.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;


    @Override
    public CartDto get() {
        return cartMapper.fromCart(cartRepository.getById(getCartId()));
    }

    @Override
    public void overwrite(CartDto cartDto) {
        /*cartRepository.deleteById(getCartId());
        cartRepository.flush();*/
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

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final String username = principal instanceof UserDetails
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        final User user = userRepository.findUserByUsername(username);
        Cart cart = user.getCart();

        if (cart == null) {
            cart = cartRepository.saveAndFlush(
                    Cart.builder()
                            .id(user.getId())
                            .build());

        }

        return cart.getId();


    }
}
