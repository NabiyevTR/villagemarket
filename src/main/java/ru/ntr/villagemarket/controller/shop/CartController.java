package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.helpers.CartItem;
import ru.ntr.villagemarket.model.service.CartService;
import ru.ntr.villagemarket.responses.Response;

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
        return cartService.get();
    }

    @PostMapping
    public Response overWrite(@RequestBody List<CartItem> cartItems) {

        cartService.overwrite(CartDto.builder()
                .cart(cartItems)
                .build());
        return Response.builder()
                .build();
    }

    @GetMapping("/checkout")
    public Response checkout() {
        cartService.checkout();
        return Response.builder()
                .build();
    }
}
