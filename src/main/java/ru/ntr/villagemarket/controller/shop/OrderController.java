package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.OrderDto;
import ru.ntr.villagemarket.model.service.CartService;
import ru.ntr.villagemarket.model.service.OrderService;
import ru.ntr.villagemarket.responses.Response;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_CUSTOMER"})
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping
    public OrderDto createNewOrder() {
       return orderService.dataForNewOrder();
    }

    @PostMapping("/")
    public Response saveOrder(@RequestBody OrderDto orderDto) {

        orderService.createOrder(orderDto);
        cartService.clearCart();

        return Response.builder()
                .build();
    }
}
