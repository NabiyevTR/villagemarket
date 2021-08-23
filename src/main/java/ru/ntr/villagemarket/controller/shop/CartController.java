package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.cart.CartDto;
import ru.ntr.villagemarket.model.helpers.ProductItem;
import ru.ntr.villagemarket.model.service.CartService;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_CUSTOMER", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPERADMIN"})

public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDto getCart() {
        return cartService.getCartDto();
    }

    @PostMapping
    public void overwriteCart(@RequestBody List<ProductItem> productItems) {
        cartService.update(CartDto.builder()
                .cart(productItems)
                .build());
    }

    @GetMapping("/checkout")
    public void checkout() {
        cartService.checkout();
    }
}
