package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.order.*;

import java.util.List;

public interface OrderService {

    void createOrder(NewOrderDto newOrderDto);

    NewOrderDto dataForNewOrder();

    List<OrderBasicDto> findAll();

    OrderWithHistoryDto findById(int id);

    void save(NewOrderDto newOrderDto);

    void delete(int id);

    List<OrderBasicDto> findAllActive();

    OrderStatusChangeResponseDto changeStatus(int id, OrderStatusChangeRequestDto orderStatusChangeRequestDto);

}
