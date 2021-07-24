package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.entity.Cart;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.helpers.CartItem;
import ru.ntr.villagemarket.model.repository.ProductRepository;


import java.util.*;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final ProductRepository productRepository;


    public Cart toCart(int cartId, CartDto cartDto) {

        return Cart.builder()
                .id(cartId)
                .products(toProductList(cartDto.getCart()))
                .build();
    }

    public CartDto fromCart(Cart cart) {

        return CartDto.builder()
                .cart(fromProductList(cart.getProducts()))
                .build();

    }

    public List<CartItem> fromProductList(List<Product> productList) {

        List<CartItem> cart = new ArrayList<>();

        if (productList == null) {
            return cart;
        }

        for (Product product : productList) {

            Optional<CartItem> optional = cart.stream().filter(e -> e.getId() == product.getId()).findFirst();

            if (optional.isEmpty()) {
                cart.add(
                        CartItem.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .quantity(1)
                                .build()
                );
            } else {
                CartItem cartItem = optional.get();
                int quantity = cartItem.getQuantity();
                cartItem.setQuantity(++quantity);
            }
        }
        return cart;
    }

    public List<Product> toProductList(List<CartItem> cart) {

        List<Product> products = new ArrayList<>();

        for (CartItem cartItem : cart) {
            products.addAll(
                    Collections.nCopies(cartItem.getQuantity(), productRepository.getById(cartItem.getId()))
            );
        }
        return products;
    }


}
