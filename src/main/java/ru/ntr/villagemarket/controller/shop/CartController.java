package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.helpers.ProductItem;
import ru.ntr.villagemarket.model.service.CartService;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
//@Secured({"ROLE_CUSTOMER"})
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDto getCart() {
        //TODO Delete
        CartDto cartDto = cartService.getCartDto();
        return cartService.getCartDto();
    }

    @PostMapping
    public void overWrite(@RequestBody List<ProductItem> productItems) {
        cartService.overwrite(CartDto.builder()
                .cart(productItems)
                .build());
    }

    @GetMapping("/checkout")
    public void checkout() {
        cartService.checkout();
    }
}
