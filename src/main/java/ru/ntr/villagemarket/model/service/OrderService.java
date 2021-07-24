package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.CartDto;
import ru.ntr.villagemarket.model.dto.OrderDto;

public interface OrderService {


    void createOrder(OrderDto orderDto);

    OrderDto dataForNewOrder();
}
