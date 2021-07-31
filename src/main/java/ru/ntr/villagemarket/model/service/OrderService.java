package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.dto.order.OrderDtoBasic;
import ru.ntr.villagemarket.model.dto.order.OrderWithHistoryDto;

import java.util.List;

public interface OrderService {


    void createOrder(NewOrderDto newOrderDto);

    NewOrderDto dataForNewOrder();

    List<OrderDtoBasic> findAll();

   OrderWithHistoryDto findById(int id);

    void save(NewOrderDto newOrderDto);

    void delete(int id);

    List<OrderDtoBasic> findAllActive();
}
