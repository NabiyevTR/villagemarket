package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.entity.Cart;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.helpers.ProductItem;
import ru.ntr.villagemarket.model.repository.ProductRepository;


import java.util.*;

@Component
@RequiredArgsConstructor
public class OrderProductsMapper {

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

    public List<ProductItem> fromProductList(List<Product> productList) {

        List<ProductItem> cart = new ArrayList<>();

        if (productList == null) {
            return cart;
        }

        for (Product product : productList) {

            Optional<ProductItem> optional = cart.stream().filter(e -> e.getId() == product.getId()).findFirst();

            if (optional.isEmpty()) {
                cart.add(
                        ProductItem.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .quantity(1)
                                .build()
                );
            } else {
                ProductItem productItem = optional.get();
                int quantity = productItem.getQuantity();
                productItem.setQuantity(++quantity);
            }
        }
        return cart;
    }

    public List<ProductItem> fromProductDtoList(List<ProductDto> productDtos) {

        List<ProductItem> cart = new ArrayList<>();

        if (productDtos == null) {
            return cart;
        }

        for (ProductDto product : productDtos) {

            Optional<ProductItem> optional = cart.stream().filter(e -> e.getId() == product.getId()).findFirst();

            if (optional.isEmpty()) {
                cart.add(
                        ProductItem.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .quantity(1)
                                .build()
                );
            } else {
                var productItem = optional.get();
                int quantity = productItem.getQuantity();
                productItem.setQuantity(++quantity);
            }
        }
        return cart;
    }


    public List<Product> toProductList(List<ProductItem> cart) {

        List<Product> products = new ArrayList<>();

        for (ProductItem productItem : cart) {
            products.addAll(
                    Collections.nCopies(productItem.getQuantity(), productRepository.getById(productItem.getId()))
            );
        }
        return products;
    }


}
