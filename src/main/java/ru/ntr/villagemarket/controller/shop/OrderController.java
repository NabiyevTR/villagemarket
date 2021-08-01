package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.service.OrderService;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
/*@Secured({"ROLE_CUSTOMER"})*/
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public NewOrderDto createNewOrder() {
       return orderService.dataForNewOrder();
    }

    @PostMapping()
    public void saveOrder(@RequestBody NewOrderDto newOrderDto) {
        log.debug(String.valueOf(newOrderDto));
        orderService.createOrder(newOrderDto);
    }
}
