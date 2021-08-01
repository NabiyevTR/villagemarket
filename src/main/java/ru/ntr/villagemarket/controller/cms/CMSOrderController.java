package ru.ntr.villagemarket.controller.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.dto.order.OrderDtoBasic;
import ru.ntr.villagemarket.model.dto.order.OrderWithHistoryDto;
import ru.ntr.villagemarket.model.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/cms/order")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_SUPERADMIN", "ROLE_MANAGER"})
public class CMSOrderController {

    private final OrderService orderService;

    @GetMapping()
    public List<OrderDtoBasic> showOrders() {
        return orderService.findAll();
    }

    @GetMapping("/active")
    public List<OrderDtoBasic> showActiveOrders() {
        return orderService.findAllActive();
    }

    @GetMapping("/{id}")
    public OrderWithHistoryDto showOrderFullInfo(@PathVariable("id") int id) {
        return orderService.findById(id);
    }


    //TODO correct types
    @PatchMapping(value = "/{id}/edit")
    public void updateOrder(@RequestBody NewOrderDto newOrderDto, @PathVariable("id") int id) {
        orderService.save(newOrderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") int id) {
        orderService.delete(id);
    }
}

