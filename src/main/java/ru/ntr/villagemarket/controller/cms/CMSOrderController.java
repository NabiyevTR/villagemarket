package ru.ntr.villagemarket.controller.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.order.*;
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
    public List<OrderBasicDto> showOrders() {
        return orderService.findAll();
    }

    @GetMapping("/active")
    public List<OrderBasicDto> showActiveOrders() {
        return orderService.findAllActive();
    }

    @GetMapping("/{id}")
    public OrderWithHistoryDto showOrderFullInfo(@PathVariable("id") int id) {
        return orderService.findById(id);
    }


   /* //TODO correct types
    @PatchMapping("/{id}/edit")
    public void updateOrder(@RequestBody NewOrderDto newOrderDto, @PathVariable("id") int id) {
        orderService.save(newOrderDto);
    }*/

    @PatchMapping("/{id}/edit/status")
    public OrderStatusChangeResponseDto updateOrderStatus(@RequestBody OrderStatusChangeRequestDto orderStatusChangeRequestDto,
                                                          @PathVariable("id") int id) {
        return orderService.changeStatus(id, orderStatusChangeRequestDto);
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") int id) {
        orderService.delete(id);
    }
}

