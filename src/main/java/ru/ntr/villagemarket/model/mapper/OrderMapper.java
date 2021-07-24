package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.OrderDto;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.helpers.CartItem;
import ru.ntr.villagemarket.model.repository.OrderStatusRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderStatusRepository orderStatusRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public Order toOrder(OrderDto orderDto) {

        return Order.builder()
                .id(orderDto.getId())
                .status(orderStatusRepository.getById(orderDto.getStatus()))
                .products(cartMapper.toProductList(orderDto.getCart()))
                .customerFirstName(orderDto.getFirstName())
                .customerLastName(orderDto.getLastName())
                .customerAddress(orderDto.getAddress())
                .customerEmail(orderDto.getEmail())
                .customerPhoneNumber(orderDto.getPhoneNumber())
                .build();
    }

    public OrderDto fromOrder(Order order) {

        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus().getId())
                .cart(cartMapper.fromProductList(order.getProducts()))
                .firstName(order.getCustomerFirstName())
                .lastName(order.getCustomerLastName())
                .address(order.getCustomerAddress())
                .email(order.getCustomerEmail())
                .phoneNumber(order.getCustomerPhoneNumber())
                .build();
    }

    public List<Order> toProducts(List<OrderDto> orderDtoList) {
        return orderDtoList.stream()
                .map(this::toOrder)
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
    }

    public List<OrderDto> fromOrders(List<Order> orderList) {
        return orderList.stream()
                .map(this::fromOrder)
                .sorted(Comparator.comparing(OrderDto::getId))
                .collect(Collectors.toList());
    }



}
